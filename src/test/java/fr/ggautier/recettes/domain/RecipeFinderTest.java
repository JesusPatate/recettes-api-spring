package fr.ggautier.recettes.domain;

import fr.ggautier.recettes.utils.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests of {@link RecipeFinder}.
 */
class RecipeFinderTest implements UnitTest {

    private final RecipeFinder service;

    @Mock
    private Recipes repository;

    RecipeFinderTest() {
        MockitoAnnotations.initMocks(this);

        this.service = new RecipeFinder();
    }

    @Test
    void testSearch() {
        // Given
        final Recipe recipe1 = new Recipe(UUID.fromString("cc83467c-8192-454f-90bb-7e88bfdd8214"), "recipe1");
        final Recipe recipe2 = new Recipe(UUID.fromString("436c61f5-0e81-4194-8e09-13f2fa043c7e"), "recipe2");

        final List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        // When
        final String term = "foo";
        final List<Recipe> results = this.service.search(term);

        // Then
        assertThat(results).containsExactly(recipe1, recipe2);
    }

    @Test
    void testSearchNoResult() {
        // Given

        // When
        final String term = "foo";
        final List<Recipe> results = this.service.search(term);

        // Then
        assertThat(results).isEmpty();
    }
}