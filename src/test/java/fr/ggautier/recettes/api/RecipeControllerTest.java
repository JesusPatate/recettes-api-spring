package fr.ggautier.recettes.api;

import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.RecipeFinder;
import fr.ggautier.recettes.domain.RecipeManager;
import fr.ggautier.recettes.utils.IntegrationTest;
import fr.ggautier.recettes.utils.ObjectBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WebMvcTest
class RecipeControllerTest implements IntegrationTest {

    @TestConfiguration
    static class RecipeControllerTestContextConfiguration {

        @Bean
        public JsonRecipeMapper jsonRecipeMapper() {
            return new JsonRecipeMapper();
        }
    }

    @Autowired
    private RecipeController controller;

    @MockBean
    private RecipeManager manager;

    @MockBean
    private RecipeFinder finder;

    /**
     * When no recipe is saved, {@link RecipeController} should return an empty list to GET requests to /recipes.
     */
    @Test
    void testGetAllNoRecipe() {
        // Given
        given(this.manager.getAll()).willReturn(Collections.emptyList());

        // When
        final List<Recipe> recipes = this.controller.getAll();

        // Then
        assertThat(recipes).isEmpty();
    }

    @Test
    void testGetAll() {
        // Given
        final Recipe recipe1 = ObjectBuilder.buildRecipe(UUID.randomUUID(), "recipe1");
        final Recipe recipe2 = ObjectBuilder.buildRecipe(UUID.randomUUID(), "recipe2");

        final List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        given(this.manager.getAll()).willReturn(recipes);

        // When
        final List<Recipe> output = this.controller.getAll();

        // Then
        assertThat(output).containsExactly(recipe1, recipe2);
    }

    @Test
    void testStore() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final JsonRecipe json = ObjectBuilder.buildJsonRecipe(id, "recipe1");

        // When
        final Recipe output = this.controller.store(json);

        // Then
        final Recipe expected = ObjectBuilder.buildRecipe(id, json.getTitle());
        assertThat(output).isEqualTo(expected);
    }

    @Test
    void testDelete() throws Exception {
        // Given
        final JsonRecipe json = ObjectBuilder.buildJsonRecipe(UUID.randomUUID(), "recipe1");

        // When
        this.controller.delete(json.getId());

        // Then
        verify(this.manager).delete(json.getId());
    }

    @Test
    void testSearch() throws Exception {
        // Given
        final Recipe recipe1 = ObjectBuilder.buildRecipe(UUID.randomUUID(), "recipe1");
        final Recipe recipe2 = ObjectBuilder.buildRecipe(UUID.randomUUID(), "recipe2");

        final List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        final String term = "foo";
        given(this.finder.search(term)).willReturn(recipes);

        // When
        final List<Recipe> output = this.controller.search(term);

        // Then
        assertThat(output).containsExactly(recipe1, recipe2);
    }

    @Test
    void testSearchNoResult() throws Exception {
        // Given
        final String term = "foo";
        given(this.finder.search(term)).willReturn(Collections.emptyList());

        // When
        final List<Recipe> output = this.controller.search(term);

        // Then
        assertThat(output).isEmpty();
    }
}
