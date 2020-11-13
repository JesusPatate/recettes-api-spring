package fr.ggautier.recettes.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class RecipeManager {

    private final Recipes recipes;

    @Autowired
    public RecipeManager(final Recipes recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getAll() {
        return this.recipes.getAll();
    }

    public void store(final Recipe recipe) {
        this.recipes.add(recipe);
        // TODO: index recipe
    }

    /**
     * Deletes a recipe.
     *
     * @param id The identifier of the recipe to be deleted
     * @throws NoSuchElementException If the provided identifier does not match with any saved recipe
     */
    public void delete(final UUID id) {
        final Recipe recipe = this.recipes.get(id)
            .orElseThrow(NoSuchElementException::new);

        this.recipes.remove(recipe);
    }
}
