package fr.ggautier.recettes.e2e;

import fr.ggautier.recettes.spi.RecipeDbModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
class GetAllRecipes {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testGetAll() throws Exception {
        // Given
        final RecipeDbModel recipe1 = new RecipeDbModel(UUID.randomUUID(), "recipe1");
        final RecipeDbModel recipe2 = new RecipeDbModel(UUID.randomUUID(), "recipe2");
        this.storeRecipes(recipe1, recipe2);

        // When
        final ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/recipes"));

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$[0].id").value(recipe1.getId().toString()))
            .andExpect(jsonPath("$[0].title").value(recipe1.getTitle()))
            .andExpect(jsonPath("$[1].id").value(recipe2.getId().toString()))
            .andExpect(jsonPath("$[1].title").value(recipe2.getTitle()));
    }

    private void storeRecipes(final RecipeDbModel... recipes) {
        Arrays.stream(recipes).forEach(this.entityManager::persist);
        this.entityManager.flush();
    }
}
