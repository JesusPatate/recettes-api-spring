package fr.ggautier.recettes.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RecipeManagementServiceTest {

    private final RecipeManagementService service;

    @Mock
    private Recipes repository;

    RecipeManagementServiceTest() {
        MockitoAnnotations.initMocks(this);

        this.service = new RecipeManagementService(this.repository);
    }

    @Test
    void testGetAllNoRecipe() {
        given(this.repository.getAll()).willReturn(Collections.emptyList());

        // When
        final List<Recipe> result = this.service.getAll();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void testGetAll() {
        // Given
        final Recipe recipe1 = new Recipe(UUID.randomUUID(), "recipe1");
        final Recipe recipe2 = new Recipe(UUID.randomUUID(), "recipe2");

        final List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        given(this.repository.getAll()).willReturn(recipes);

        // When
        final List<Recipe> result = this.service.getAll();

        // Then
        assertThat(result).containsExactly(recipe1, recipe2);
    }

    @Test
    void testStore() {
        // Given
        final Recipe recipe = new Recipe(UUID.randomUUID(), "recipe1");

        // When
        this.service.store(recipe);

        // Then
        verify(this.repository).save(recipe);
    }

    @Test
    void testDelete() {
        // Given
        final Recipe recipe = new Recipe(UUID.randomUUID(), "recipe1");

        given(this.repository.find(recipe.getId())).willReturn(Optional.of(recipe));

        // When
        this.service.delete(recipe.getId());

        // Then
        verify(this.repository).delete(recipe);
    }
}