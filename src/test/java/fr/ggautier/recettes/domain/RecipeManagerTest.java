package fr.ggautier.recettes.domain;

import fr.ggautier.recettes.utils.ObjectBuilder;
import fr.ggautier.recettes.utils.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RecipeManagerTest implements UnitTest {

    private RecipeManager service;

    @Mock
    private IStoreRecipes recipes;

    RecipeManagerTest() {
        MockitoAnnotations.initMocks(this);
        this.service = new RecipeManager(this.recipes);
    }

    @Test
    void testStore() {
        // Given
        final Recipe recipe = ObjectBuilder.buildRecipe(UUID.randomUUID(), "recipe1");

        // When
        this.service.store(recipe);

        // Then
        verify(this.recipes).add(recipe);
    }

    @Test
    void testDelete() {
        // Given
        final Recipe recipe = ObjectBuilder.buildRecipe(UUID.randomUUID(), "recipe1");

        given(this.recipes.get(recipe.getId())).willReturn(Optional.of(recipe));

        // When
        this.service.delete(recipe.getId());

        // Then
        verify(this.recipes).remove(recipe);
    }
}