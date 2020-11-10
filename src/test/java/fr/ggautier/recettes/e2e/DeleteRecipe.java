package fr.ggautier.recettes.e2e;

import fr.ggautier.recettes.spi.RecipeDbModel;
import fr.ggautier.recettes.utils.EndToEndTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The application should allow users to delete existing recipes.
 */
class DeleteRecipe extends EndToEndTest {

    @Test
    void testDelete() throws Exception {
        // Given
        final RecipeDbModel recipe1 = new RecipeDbModel(UUID.randomUUID(), "recipe1");
        final RecipeDbModel recipe2 = new RecipeDbModel(UUID.randomUUID(), "recipe2");
        this.store(recipe1, recipe2);

        // When
        final ResultActions actions = this.mvc.perform(
            MockMvcRequestBuilders.delete("/recipes/{id}", recipe1.getId()));

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isOk());

        final List<RecipeDbModel> recipes = this.getAllRecipes();

        assertThat(recipes).containsExactly(recipe2);
    }
}
