package fr.ggautier.recettes.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class RecipeManagementService {

    private final Recipes recipes;

    @Autowired
    public RecipeManagementService(final Recipes recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getAll() {
        return this.recipes.getAll();
    }

    public void store(final Recipe recipe) {
        this.recipes.save(recipe);
    }

    /**
     * Deletes a recipe.
     *
     * @param id The identifier of the recipe to be deleted
     * @throws NoSuchElementException If the provided identifier does not match with any saved recipe
     */
    public void delete(final UUID id) {
        final Recipe recipe = this.recipes.find(id)
            .orElseThrow(NoSuchElementException::new);

        this.recipes.delete(recipe);
    }
}
