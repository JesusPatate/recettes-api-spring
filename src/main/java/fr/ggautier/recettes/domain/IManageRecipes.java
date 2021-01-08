package fr.ggautier.recettes.domain;

import fr.ggautier.arch.annotations.Port;

import java.util.NoSuchElementException;
import java.util.UUID;

@Port(type = Port.Type.API)
public interface IManageRecipes {

    void store(Recipe recipe);

    /**
     * Deletes a recipe.
     *
     * @param id The identifier of the recipe to be deleted
     * @throws NoSuchElementException If the provided identifier does not match with any saved recipe
     */
    void delete(UUID id);
}
