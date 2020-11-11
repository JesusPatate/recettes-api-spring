package fr.ggautier.recettes.spi;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.spi.db.DbRecipe;
import fr.ggautier.recettes.spi.db.RecipeDAO;
import fr.ggautier.recettes.spi.es.EsClient;
import fr.ggautier.recettes.spi.es.EsRecipeMapper;
import fr.ggautier.recettes.utils.IntegrationTest;
import org.apache.http.HttpHost;
import org.assertj.core.api.SoftAssertions;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RecipeRepositoryTest implements IntegrationTest {

    private RecipeRepository repository;

    @Autowired
    private RecipeDAO dao;

    @Autowired
    private EsClient esClient;

    @Autowired
    private EsRecipeMapper esRecipeMapper;

    @Autowired
    private TestEntityManager entityManager;

    @Value("${es.host}")
    private String esHost;

    @Value("${es.port}")
    private Integer esPort;

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        public EsClient esClient() {
            return new EsClient();
        }

        @Bean
        public EsRecipeMapper esRecipeMapper() {
            return new EsRecipeMapper();
        }
    }

    @BeforeEach
    void setUp() {
        this.repository = new RecipeRepository(this.dao, this.esClient, esRecipeMapper);
    }

    @Test
    void testGetAll() {
        // Given
        final DbRecipe recipe1 = new DbRecipe(UUID.randomUUID(), "recipe1");
        final DbRecipe recipe2 = new DbRecipe(UUID.randomUUID(), "recipe2");
        this.storeInDB(recipe1, recipe2);

        // When
        final List<Recipe> result = this.repository.getAll();

        // Then
        assertThat(result).hasSize(2)
            .anyMatch(recipe -> recipe.getId().equals(recipe1.getId()) &&
                recipe.getTitle().equals(recipe1.getTitle()))
            .anyMatch(recipe -> recipe.getId().equals(recipe2.getId()) &&
                recipe.getTitle().equals(recipe2.getTitle()));
    }

    @Test
    void testGet() {
        // Given
        final DbRecipe recipe = new DbRecipe(UUID.randomUUID(), "recipe1");
        this.storeInDB(recipe);

        // When
        final Optional<Recipe> result = this.repository.get(recipe.getId());

        // Then
        final Recipe expected = new Recipe(recipe.getId(), recipe.getTitle());

        assertThat(result).contains(expected);
    }

    @Test
    void testAdd() {
        // Given
        final Recipe recipe = new Recipe(UUID.randomUUID(), "recipe1");

        // When
        this.repository.add(recipe);

        // Then
        final DbRecipe dbModel = this.entityManager.find(DbRecipe.class, recipe.getId());

        assertThat(dbModel).isNotNull();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dbModel.getId()).isEqualTo(recipe.getId());
            softly.assertThat(dbModel.getTitle()).isEqualTo(recipe.getTitle());
        });
    }

    @Test
    void testRemove() {
        // Given
        final DbRecipe recipe1 = new DbRecipe(UUID.randomUUID(), "recipe1");
        final DbRecipe recipe2 = new DbRecipe(UUID.randomUUID(), "recipe2");
        this.storeInDB(recipe1, recipe2);

        // When
        final Recipe recipe = new Recipe(recipe1.getId(), recipe1.getTitle());
        this.repository.remove(recipe);

        // Then
        final DbRecipe dbModel1 = this.entityManager.find(DbRecipe.class, recipe1.getId());

        assertThat(dbModel1).isNull();

        final DbRecipe dbModel2 = this.entityManager.find(DbRecipe.class, recipe2.getId());

        assertThat(dbModel2).isEqualTo(recipe2);
    }

    @Test
    void testSearch() throws IOException {
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
        this.storeInES(recipes);

        // When
        final List<Recipe> results = this.repository.search("bar");

        // Then
        final Recipe expected1 = new Recipe(recipe1.getId(), recipe1.getTitle());
        final Recipe expected2 = new Recipe(recipe2.getId(), recipe2.getTitle());
        final Recipe[] expected = {expected1, expected2};

        assertThat(results).containsExactly(expected);
    }

    @Test
    void testSearchNoResult() throws IOException {
        // Given

        // When
        final List<Recipe> results = this.repository.search("This should not appear in any recipe");

        // Then
        assertThat(results).isEmpty();
    }

    private void storeInDB(final DbRecipe... recipes) {
        Arrays.stream(recipes).forEach(this.entityManager::persist);
        this.entityManager.flush();
    }

    private void storeInES(final DbRecipe... recipes) throws IOException {
        final RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                new HttpHost(this.esHost, this.esPort, "http")
            ));

        for (DbRecipe dbModel : recipes) {
            final IndexRequest request = new IndexRequest("recipes", "recipe", dbModel.getId().toString());
            final String json = new ObjectMapper().writeValueAsString(dbModel);
            request.source(json, XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
        }

        client.close();
    }
}