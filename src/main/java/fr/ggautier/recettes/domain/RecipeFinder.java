package fr.ggautier.recettes.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeFinder {

    private final Recipes recipes;

    @Autowired
    public RecipeFinder(final Recipes recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> search(final String term) throws Exception {
        return this.recipes.search(term);
    }
}
