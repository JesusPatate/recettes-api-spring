package fr.ggautier.recettes.domain;

import java.util.List;

public interface Recipes {

    List<Recipe> getAll();

    void save(final Recipe recipe);
}
