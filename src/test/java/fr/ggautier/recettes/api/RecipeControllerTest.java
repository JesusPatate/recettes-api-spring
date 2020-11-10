package fr.ggautier.recettes.api;

import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.RecipeManager;
import fr.ggautier.recettes.utils.IntegrationTest;
import fr.ggautier.recettes.utils.JsonRecipeBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RecipeControllerTest implements IntegrationTest {

    private RecipeController controller;

    @Mock
    private RecipeManager manager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.controller = new RecipeController(this.manager);
    }

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
        final Recipe recipe1 = new Recipe(UUID.randomUUID(), "recipe1");
        final Recipe recipe2 = new Recipe(UUID.randomUUID(), "recipe2");

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
        final JsonRecipe json = this.buildJsonRecipe(id, "recipe1");

        // When
        final Recipe output = this.controller.store(id, json);

        // Then
        final Recipe expected = new Recipe(id, json.getTitle());
        assertThat(output).isEqualTo(expected);
    }

    @Test
    void testStoreNotMatchingIds() {
        // Given
        final JsonRecipe json = this.buildJsonRecipe(UUID.randomUUID(), "recipe1");

        // When
        final Throwable throwable = catchThrowable(() -> this.controller.store(UUID.randomUUID(), json));

        // Then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(NotMatchingIdentifiersException.class);
        assertThat(throwable).hasMessage(NotMatchingIdentifiersException.MESSAGE);
    }

    @Test
    void testDelete() throws Exception {
        // Given
        final JsonRecipe json = buildJsonRecipe(UUID.randomUUID(), "recipe1");

        // When
        this.controller.delete(json.getId());

        // Then
        verify(this.manager).delete(json.getId());
    }

    private JsonRecipe buildJsonRecipe(
        final UUID id,
        final String title,
        final JsonIngredient... ingredients
    ) {
        final JsonRecipeBuilder builder = new JsonRecipeBuilder()
            .setId(id)
            .setTitle(title);

        for (JsonIngredient ingredient : ingredients) {
            builder.addIngredient(ingredient);
        }

        return builder.build();
    }
}
