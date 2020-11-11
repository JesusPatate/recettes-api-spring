package fr.ggautier.recettes.spi.db;

import fr.ggautier.recettes.domain.Recipe;
import org.springframework.stereotype.Component;

@Component
public class DbRecipeMapper {

    /**
     * Builds a DB representation of a recipe.
     *
     * @param recipe The recipe to convert into a DB representation
     */
    public DbRecipe toDbModel(final Recipe recipe) {
        return new DbRecipe(recipe.getId(), recipe.getTitle());
    }

    /**
     * Reconstructs a recipe from its DB representation.
     *
     * @param dbModel The DB representation of the recipe to be reconstructed
     */
    public Recipe fromDbModel(final DbRecipe dbModel) {
        return new Recipe(dbModel.getId(), dbModel.getTitle());
    }
}