package fr.ggautier.recettes.domain;

import fr.ggautier.arch.annotations.Port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Port(type = Port.Type.API)
public interface ICanFindRecipes {
    List<Recipe> getAll();

    Optional<Recipe> get(final UUID id);

    List<Recipe> search(String term) throws Exception;
}
