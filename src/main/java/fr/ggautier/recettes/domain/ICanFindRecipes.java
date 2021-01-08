package fr.ggautier.recettes.domain;

import fr.ggautier.arch.annotations.Port;

import java.util.List;

@Port(type = Port.Type.API)
public interface ICanFindRecipes {
    List<Recipe> getAll();

    List<Recipe> search(String term) throws Exception;
}
