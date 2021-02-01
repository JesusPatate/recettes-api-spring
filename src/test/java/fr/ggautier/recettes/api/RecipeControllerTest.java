package fr.ggautier.recettes.api;

import fr.ggautier.recettes.domain.ICanFindRecipes;
import fr.ggautier.recettes.domain.IManageRecipes;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.utils.ObjectBuilder;
import fr.ggautier.recettes.utils.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RecipeControllerTest implements UnitTest {

    private final RecipeController controller;

    @Mock
    private IManageRecipes managementService;

    @Mock
    private ICanFindRecipes browsingService;

    RecipeControllerTest() {
        MockitoAnnotations.initMocks(this);

        final RecipeMapper mapper = new RecipeMapper();
        this.controller = new RecipeController(
            new RecipesApiAdapter(this.managementService, this.browsingService, mapper)
        );
    }

    /**
     * When no recipe is saved, {@link RecipeController} should return an empty list to GET requests to /recipes.
     */
    @Test
    void testGetAllNoRecipe() {
        // Given
        given(this.browsingService.getAll()).willReturn(Collections.emptyList());

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

        given(this.browsingService.getAll()).willReturn(recipes);

        // When
        final List<Recipe> output = this.controller.getAll();

        // Then
        assertThat(output).containsExactly(recipe1, recipe2);
    }

    @Test
    void testGet() {
        // Given
        final UUID id = UUID.randomUUID();
        final String title = UUID.randomUUID().toString();
        final Recipe recipe = ObjectBuilder.buildRecipe(id, title);

        given(this.browsingService.get(id)).willReturn(Optional.of(recipe));

        // When
        final OutputRecipeDto output = this.controller.get(id);

        // Then
        final OutputRecipeDto expected = ObjectBuilder.buildJsonRecipe(id, title);
        assertThat(output).isEqualTo(expected);
    }

    @Test
    void testStore() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final OutputRecipeDto json = ObjectBuilder.buildJsonRecipe(id, "recipe1");

        // When
        final OutputRecipeDto output = this.controller.store(json);

        // Then
        assertThat(output).isEqualTo(json);
    }

    @Test
    void testDelete() throws Exception {
        // Given
        final OutputRecipeDto json = ObjectBuilder.buildJsonRecipe(UUID.randomUUID(), "recipe1");

        // When
        this.controller.delete(json.getId());

        // Then
        verify(this.managementService).delete(json.getId());
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
        given(this.browsingService.search(term)).willReturn(recipes);

        // When
        final List<OutputRecipeDto> output = this.controller.search(term);

        // Then
        final OutputRecipeDto expected1 = ObjectBuilder.buildJsonRecipe(recipe1.getId(), recipe1.getTitle());
        final OutputRecipeDto expected2 = ObjectBuilder.buildJsonRecipe(recipe2.getId(), recipe2.getTitle());
        assertThat(output).containsExactly(expected1, expected2);
    }

    @Test
    void testSearchNoResult() throws Exception {
        // Given
        final String term = "foo";
        given(this.browsingService.search(term)).willReturn(Collections.emptyList());

        // When
        final List<OutputRecipeDto> output = this.controller.search(term);

        // Then
        assertThat(output).isEmpty();
    }
}
