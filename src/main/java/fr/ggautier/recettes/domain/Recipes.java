package fr.ggautier.recettes.domain;

import fr.ggautier.arch.annotations.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface Recipes {

    List<Recipe> getAll();

    Optional<Recipe> get(final UUID id);

    void add(final Recipe recipe);

    void remove(final Recipe id);
}
