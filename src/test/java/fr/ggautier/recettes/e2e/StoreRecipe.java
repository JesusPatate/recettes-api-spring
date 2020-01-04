package fr.ggautier.recettes.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ggautier.recettes.api.JsonRecipe;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.Recipes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@Transactional
class StoreRecipe {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Recipes repository;

    @Test
    void testStore() throws Exception {
        // Given
        final JsonRecipe recipe = new JsonRecipe(UUID.randomUUID(), "recipe1");
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

        final List<Recipe> recipes = this.repository.getAll();
        final Recipe expected = new Recipe(recipe.getId(), recipe.getTitle());

        assertThat(recipes).containsExactly(expected);
    }
}
