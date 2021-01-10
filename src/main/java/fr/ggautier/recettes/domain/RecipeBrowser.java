package fr.ggautier.recettes.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecipeBrowser implements ICanFindRecipes {

    private final IStoreRecipes recipes;

    @Autowired
    public RecipeBrowser(final IStoreRecipes recipes) {
        this.recipes = recipes;
    }

    @Override
    public List<Recipe> getAll() {
        return this.recipes.getAll();
    }

    @Override
    public Optional<Recipe> get(final UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Recipe> search(final String term) throws Exception {
        return this.recipes.search(term);
    }
}
