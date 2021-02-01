package fr.ggautier.recettes.utils;

import fr.ggautier.recettes.api.IngredientDto;
import fr.ggautier.recettes.api.OutputRecipeDto;
import fr.ggautier.recettes.domain.Ingredient;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.spi.db.DbIngredient;
import fr.ggautier.recettes.spi.db.DbRecipe;

import java.util.Arrays;
import java.util.UUID;

public final class ObjectBuilder {

    public static Recipe buildRecipe(UUID id, String title, Ingredient... ingredients) {
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

    public static DbRecipe buildDbRecipe(UUID id, String title, DbIngredient... ingredients) {
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

    public static OutputRecipeDto buildJsonRecipe(UUID id, String title, IngredientDto... ingredients) {
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
