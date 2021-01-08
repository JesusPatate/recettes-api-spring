package fr.ggautier.recettes.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class RecipeManager implements IManageRecipes {

    private final IStoreRecipes recipes;

    @Autowired
    public RecipeManager(final IStoreRecipes recipes) {
        this.recipes = recipes;
    }

    @Override
    public void store(final Recipe recipe) {
        this.recipes.add(recipe);
    }

    @Override
    public void delete(final UUID id) {
        final Recipe recipe = this.recipes.get(id)
            .orElseThrow(NoSuchElementException::new);

        this.recipes.remove(recipe);
    }
}
