package fr.ggautier.recettes.domain;

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

/**
 * Tests of {@link RecipeBrowser}.
 */
class RecipeBrowserTest implements UnitTest {

    private final RecipeBrowser service;

    @Mock
    private IStoreRecipes recipes;

    RecipeBrowserTest() {
        MockitoAnnotations.initMocks(this);

        this.service = new RecipeBrowser(this.recipes);
    }

    @Test
    void testGetAll() {
        // Given
        final Recipe recipe1 = ObjectBuilder.buildRecipe(UUID.randomUUID(), "recipe1");
        final Recipe recipe2 = ObjectBuilder.buildRecipe(UUID.randomUUID(), "recipe2");

        final List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        given(this.recipes.getAll()).willReturn(recipes);

        // When
        final List<Recipe> result = this.service.getAll();

        // Then
        assertThat(result).containsExactly(recipe1, recipe2);
    }

    @Test
    void testGetAllNoRecipe() {
        given(this.recipes.getAll()).willReturn(Collections.emptyList());

        // When
        final List<Recipe> result = this.service.getAll();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void testGet() {
        // Given
        final Recipe recipe = ObjectBuilder.buildRecipe(UUID.randomUUID(), "recipe");

        given(this.recipes.get(recipe.getId())).willReturn(Optional.of(recipe));

        // When
        final Optional<Recipe> result = this.service.get(recipe.getId());

        // Then
        assertThat(result).contains(recipe);
    }

    @Test
    void testGetNoRecipe() {
        final UUID id = UUID.randomUUID();
        given(this.recipes.get(id)).willReturn(Optional.empty());

        // When
        final Optional<Recipe> result = this.service.get(id);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void testSearch() throws Exception {
        // Given
        final Recipe recipe1 = ObjectBuilder.buildRecipe(UUID.fromString("cc83467c-8192-454f-90bb-7e88bfdd8214"), "recipe1");
        final Recipe recipe2 = ObjectBuilder.buildRecipe(UUID.fromString("436c61f5-0e81-4194-8e09-13f2fa043c7e"), "recipe2");

        final List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        final String term = "foo";
        given(this.recipes.search(term)).willReturn(recipes);

        // When
        final List<Recipe> results = this.service.search(term);

        // Then
        assertThat(results).containsExactly(recipe1, recipe2);
    }

    @Test
    void testSearchNoResult() throws Exception {
        // Given
        final String term = "foo";
        given(this.recipes.search(term)).willReturn(Collections.emptyList());

        // When
        final List<Recipe> results = this.service.search(term);

        // Then
        assertThat(results).isEmpty();
    }
}