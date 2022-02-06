package fr.ggautier.recettes.e2e;

import fr.ggautier.recettes.spi.db.DbRecipe;
import fr.ggautier.recettes.utils.EndToEndTest;
import fr.ggautier.recettes.utils.ObjectBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The application should allow users to list all the recipes.
 */
class GetOneRecipe extends EndToEndTest {

    @Test
    void testGet() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final String title = UUID.randomUUID().toString();
        final DbRecipe recipe = ObjectBuilder.buildDbRecipe(id, title);
        this.store(recipe);

        // When
        final ResultActions actions = this.mvc.perform(get("/recipes/" + id));

        // Then
        actions.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.toString()))
            .andExpect(jsonPath("$.title").value(title));
    }
}
