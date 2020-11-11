package fr.ggautier.recettes.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ggautier.recettes.spi.db.DbRecipe;
import fr.ggautier.recettes.utils.EndToEndTest;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.stream.Collectors;

class SearchRecipes extends EndToEndTest {

    @Test
    void testSearch() throws Exception {
        // Given
        final DbRecipe recipe1 = new DbRecipe(
            UUID.fromString("c11f9300-94d8-46c0-b903-40871b99305b"),
            "Foo bar"
        );
        final DbRecipe recipe2 = new DbRecipe(
            UUID.fromString("c12f9300-94d8-46c0-b903-40871b99305b"),
            "Bar à vin"
        );
        final DbRecipe recipe3 = new DbRecipe(
            UUID.fromString("c13f9300-94d8-46c0-b903-40871b99305b"),
            "Barack Obama"
        );

        final DbRecipe[] recipes = {recipe1, recipe2, recipe3};
        this.store(recipes);
        this.storeInES(recipes);

        // When
        final String url = "/recipes/search?value=bar";
        final ResultActions actions = this.mvc.perform(MockMvcRequestBuilders.post(url));
        final MockHttpServletResponse response = actions.andReturn().getResponse();

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isOk());

        final String filePath = "/fixtures/search-output.json";
        final String expected = this.readResourceFile(filePath);

        JSONAssert.assertEquals(expected, response.getContentAsString(), false);
    }

    private void storeInES(final DbRecipe... recipes) throws IOException {
        final RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                new HttpHost("localhost", 9200, "http")
            ));

        for (DbRecipe dbModel : recipes) {
            final IndexRequest request = new IndexRequest("recipes", "recipe", dbModel.getId().toString());
            final String json = new ObjectMapper().writeValueAsString(dbModel);
            request.source(json, XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
        }

        client.close();
    }

    private String readResourceFile(final String filePath) throws IOException {
        final InputStream input = this.getClass().getResourceAsStream(filePath);
        final String contents;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            contents = reader.lines().collect(Collectors.joining("\n"));
        }

        return contents;
    }
}
