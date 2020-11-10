package fr.ggautier.recettes.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ggautier.recettes.api.JsonIngredient;
import fr.ggautier.recettes.api.JsonRecipe;
import fr.ggautier.recettes.spi.RecipeDbModel;
import fr.ggautier.recettes.utils.EndToEndTest;
import fr.ggautier.recettes.utils.JsonRecipeBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * The application shoud allow users to save new recipes.
 */
class StoreRecipe extends EndToEndTest {

    @Test
    void testStore() throws Exception {
        // Given
        final JsonRecipe recipe = this.buildRecipe(
            UUID.randomUUID(),
            "recipe1",
            new JsonIngredient("ingredient 1", null, null),
            new JsonIngredient("ingredient 2", 2, null),
            new JsonIngredient("ingredient 3", 10, UUID.randomUUID())
        );

        final String json = new ObjectMapper().writeValueAsString(recipe);

        // When
        final ResultActions actions = mvc.perform(
            MockMvcRequestBuilders.put("/recipes/{id}", recipe.getId())
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.id").value(recipe.getId().toString()))
            .andExpect(jsonPath("$.title").value(recipe.getTitle()));

        final List<RecipeDbModel> recipes = this.getAllRecipes();
        final RecipeDbModel expected = new RecipeDbModel(recipe.getId(), recipe.getTitle());

        assertThat(recipes).containsExactly(expected);
    }

    @Test
    void testStoreInvalidRecipe() throws Exception {
        // Given
        final JsonRecipe recipe = this.buildRecipe(
            UUID.randomUUID(),
            ""
        );

        final String json = new ObjectMapper().writeValueAsString(recipe);

        // When
        final ResultActions actions = mvc.perform(
            MockMvcRequestBuilders.put("/recipes/{id}", recipe.getId())
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(jsonPath("$.title").value("must not be blank"))
            .andExpect(jsonPath("$.ingredients").value("must not be empty"))
            .andExpect(jsonPath("$.length()").value(2));
    }

    private JsonRecipe buildRecipe(final UUID id, final String title, final JsonIngredient... ingredients) {
        final JsonRecipeBuilder builder = new JsonRecipeBuilder()
            .setId(id)
            .setTitle(title);

        for (final JsonIngredient ingredient : ingredients) {
            builder.addIngredient(ingredient);
        }

        return builder.build();
    }
}
