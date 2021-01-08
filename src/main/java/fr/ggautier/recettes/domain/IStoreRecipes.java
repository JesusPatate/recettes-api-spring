package fr.ggautier.recettes.domain;

import fr.ggautier.arch.annotations.Port;
import fr.ggautier.arch.annotations.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Port(type = Port.Type.SPI)
public interface IStoreRecipes {

    List<Recipe> getAll();

    Optional<Recipe> get(final UUID id);

    List<Recipe> search(final String term) throws Exception;

    void add(final Recipe recipe);

    void remove(final Recipe id);
}
