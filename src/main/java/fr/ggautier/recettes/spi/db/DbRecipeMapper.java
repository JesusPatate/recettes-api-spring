package fr.ggautier.recettes.spi.db;

import fr.ggautier.recettes.domain.Ingredient;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.UnknownUnitException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DbRecipeMapper {

    /**
     * Reconstructs a recipe from its DB representation.
     *
     * @param dbModel The DB representation of the recipe to be reconstructed
     */
    public Recipe fromDbModel(final DbRecipe dbModel) throws UnknownUnitException {
        final Recipe.Builder builder = new Recipe.Builder()
            .setId(dbModel.getId())
            .setTitle(dbModel.getTitle())
            .isHot(dbModel.getHot())
            .isADessert(dbModel.getDessert())
            .setPreparationTime(dbModel.getPreparationTime())
            .setCookingTime(dbModel.getCookingTime())
            .setServings(dbModel.getServings())
            .setSource(dbModel.getSource());

        for (DbIngredient i : dbModel.getIngredients()) {
            final Ingredient ingredient = this.fromDbModel(i);
            builder.setIngredient(ingredient);
        }

        return builder.build();
    }

    /**
     * Builds a DB representation of a recipe.
     *
     * @param recipe The recipe to convert into a DB representation
     */
    public DbRecipe toDbModel(final Recipe recipe) {
        final List<DbIngredient> ingredients = recipe.getIngredients().stream()
            .map(this::toDbModel)
            .collect(Collectors.toList());

        return new DbRecipe(
            recipe.getId(),
            recipe.getTitle(),
            recipe.getHot(),
            recipe.getDessert(),
            recipe.getPreparationTime(),
            recipe.getCookingTime(),
            recipe.getServings(),
            recipe.getSource(),
            ingredients
        );
    }

    private Ingredient fromDbModel(final DbIngredient dbModel) throws UnknownUnitException {
        return new Ingredient(dbModel.getName(), dbModel.getAmount(), dbModel.getUnit());
    }

    private DbIngredient toDbModel(final Ingredient ingredient) {
        final String unit = ingredient.getUnit().map(Enum::name).orElse(null);
        final Integer amount = ingredient.getAmount().orElse(null);
        return new DbIngredient(ingredient.getName(), amount, unit);
    }
}