package fr.ggautier.recettes.domain;

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

class RecipeManagerTest implements UnitTest {

    private final RecipeManager service;

    @Mock
    private Recipes repository;

    RecipeManagerTest() {
        MockitoAnnotations.initMocks(this);

        this.service = new RecipeManager(this.repository);
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
        verify(this.repository).add(recipe);
    }

    @Test
    void testDelete() {
        // Given
        final Recipe recipe = new Recipe(UUID.randomUUID(), "recipe1");

        given(this.repository.get(recipe.getId())).willReturn(Optional.of(recipe));

        // When
        this.service.delete(recipe.getId());

        // Then
        verify(this.repository).remove(recipe);
    }
}