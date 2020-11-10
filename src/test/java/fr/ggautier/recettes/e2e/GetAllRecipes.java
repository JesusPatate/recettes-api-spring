package fr.ggautier.recettes.e2e;

import fr.ggautier.recettes.spi.RecipeDbModel;
import fr.ggautier.recettes.utils.EndToEndTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * The application should allow users to list all the recipes.
 */
class GetAllRecipes extends EndToEndTest {

    @Test
    void testGetAll() throws Exception {
        // Given
        final RecipeDbModel recipe1 = new RecipeDbModel(UUID.randomUUID(), "recipe1");
        final RecipeDbModel recipe2 = new RecipeDbModel(UUID.randomUUID(), "recipe2");
        this.store(recipe1, recipe2);

        // When
        final ResultActions actions = this.mvc.perform(MockMvcRequestBuilders.get("/recipes"));

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$[0].id").value(recipe1.getId().toString()))
            .andExpect(jsonPath("$[0].title").value(recipe1.getTitle()))
            .andExpect(jsonPath("$[1].id").value(recipe2.getId().toString()))
            .andExpect(jsonPath("$[1].title").value(recipe2.getTitle()));
    }
}
