package fr.ggautier.recettes.utils;

import fr.ggautier.recettes.api.IngredientDto;
import fr.ggautier.recettes.api.InputRecipeDto;
import fr.ggautier.recettes.api.OutputRecipeDto;
import fr.ggautier.recettes.domain.Ingredient;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.spi.db.DbIngredient;
import fr.ggautier.recettes.spi.db.DbRecipe;

import java.util.Arrays;
import java.util.UUID;

public final class ObjectBuilder {

    public static Recipe buildRecipe(final UUID id, final String title, final Ingredient... ingredients) {
        final Recipe.Builder builder = new Recipe.Builder()
            .setId(id)
            .setTitle(title)
            .isHot(true)
            .isADessert(true)
            .setPreparationTime(1)
            .setCookingTime(1)
            .setServings(1)
            .setSource("Nowhere");

        Arrays.asList(ingredients).forEach(builder::setIngredient);

        return builder.build();
    }

    public static DbRecipe buildDbRecipe(final UUID id, final String title, final DbIngredient... ingredients) {
        final DbRecipe.Builder builder = new DbRecipe.Builder()
            .setId(id)
            .setTitle(title)
            .isHot(true)
            .isADessert(true)
            .setPreparationTime(1)
            .setCookingTime(1)
            .setServings(1)
            .setSource("Nowhere");

        Arrays.asList(ingredients).forEach(builder::setIngredient);

        return builder.build();
    }

    public static InputRecipeDto buildInputJsonRecipe(final String title, final IngredientDto... ingredients) {
        final InputRecipeDto.Builder builder = new InputRecipeDto.Builder()
            .setTitle(title)
            .isHot(true)
            .isADessert(true)
            .setPreparationTime(1)
            .setCookingTime(1)
            .setServings(1)
            .setSource("Nowhere");

        Arrays.asList(ingredients).forEach(builder::setIngredient);

        return builder.build();
    }

    public static OutputRecipeDto buildOutputJsonRecipe(
        final UUID id,
        final String title,
        final IngredientDto... ingredients
    ) {
        final OutputRecipeDto.Builder builder = new OutputRecipeDto.Builder()
            .setId(id)
            .setTitle(title)
            .isHot(true)
            .isADessert(true)
            .setPreparationTime(1)
            .setCookingTime(1)
            .setServings(1)
            .setSource("Nowhere");

        Arrays.asList(ingredients).forEach(builder::setIngredient);

        return builder.build();
    }
}
