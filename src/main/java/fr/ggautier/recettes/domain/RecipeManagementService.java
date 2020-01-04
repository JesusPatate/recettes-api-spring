package fr.ggautier.recettes.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
