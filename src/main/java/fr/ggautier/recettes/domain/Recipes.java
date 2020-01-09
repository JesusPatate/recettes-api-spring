package fr.ggautier.recettes.domain;

import fr.ggautier.arch.annotations.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface Recipes {

    List<Recipe> getAll();

    Optional<Recipe> find(final UUID id);

    void save(final Recipe recipe);

    void delete(final Recipe id);
}
