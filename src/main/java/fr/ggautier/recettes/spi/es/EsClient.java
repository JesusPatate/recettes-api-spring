package fr.ggautier.recettes.spi.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EsClient {

    private final static Logger LOG = LoggerFactory.getLogger(EsClient.class);

    public void index(final EsRecipe recipe) throws IOException {
        final RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                new HttpHost("localhost", 9200, "http")
            ));

        final IndexRequest request = new IndexRequest("recipes", "recipe", recipe.getId());
        final String json = new ObjectMapper().writeValueAsString(recipe);
        request.source(json, XContentType.JSON);
        client.index(request, RequestOptions.DEFAULT);

        client.close();
    }

    public List<EsRecipe> searchRecipe(final String term) throws IOException {
        final RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                new HttpHost("localhost", 9200, "http")
            ));

        final SearchSourceBuilder query = new SearchSourceBuilder()
            .query(QueryBuilders.matchQuery("title_ingredients", term));

        final SearchRequest request = new SearchRequest("recipes").source(query);
        final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        final SearchHits hits = response.getHits();
        final List<EsRecipe> recipes = new ArrayList<>();
        final ObjectMapper objectMapper = new ObjectMapper();

        for (final SearchHit hit : hits) {
            final String source = hit.getSourceAsString();
            final EsRecipe recipe = objectMapper.readValue(source, EsRecipe.class);
            recipes.add(recipe);
        }

        return recipes;
    }
}
