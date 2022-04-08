package fr.ggautier.recettes.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ggautier.recettes.api.IngredientDto;
import fr.ggautier.recettes.api.InputRecipeDto;
import fr.ggautier.recettes.domain.Unit;
import fr.ggautier.recettes.spi.db.DbIngredient;
import fr.ggautier.recettes.spi.db.DbRecipe;
import fr.ggautier.recettes.utils.EndToEndTest;
import fr.ggautier.recettes.utils.ObjectBuilder;
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
        final UUID id = UUID.randomUUID();
        final InputRecipeDto inputDto = ObjectBuilder.buildInputJsonRecipe(
            "recipe1",
            new IngredientDto("ingredient 1", null, null),
            new IngredientDto("ingredient 2", 2, null),
            new IngredientDto("ingredient 3", 10, Unit.GRAMS.name())
        );

        // When
        final String json = new ObjectMapper().writeValueAsString(inputDto);
        final ResultActions actions = this.mvc.perform(
            MockMvcRequestBuilders.put("/recipes/" + id)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.title").value(inputDto.getTitle()));

        final List<DbRecipe> recipes = this.getAllRecipes();
        final DbRecipe expected = this.toDbRecipe(id, inputDto);

        assertThat(recipes).containsExactly(expected);
    }

    @Test
    void testStoreInvalidRecipe() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final InputRecipeDto inputDto = ObjectBuilder.buildInputJsonRecipe("");
        final String json = new ObjectMapper().writeValueAsString(inputDto);

        // When
        final ResultActions actions = this.mvc.perform(
            MockMvcRequestBuilders.put("/recipes/" + id)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(jsonPath("$.title").value("must not be blank"))
            .andExpect(jsonPath("$.ingredients").value("must not be empty"))
            .andExpect(jsonPath("$.length()").value(2));
    }

    private DbRecipe toDbRecipe(final UUID id, final InputRecipeDto recipe) {
        final DbIngredient[] dbIngredients = recipe.getIngredients().stream()
            .map(ingredient -> new DbIngredient(
                ingredient.name(),
                ingredient.amount(),
                ingredient.unit()
            )).toArray(DbIngredient[]::new);

        return ObjectBuilder.buildDbRecipe(id, recipe.getTitle(), dbIngredients);
    }
}
