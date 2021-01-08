package fr.ggautier.recettes.api;

import fr.ggautier.recettes.domain.Ingredient;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.UnknownUnitException;
import org.springframework.stereotype.Component;

@Component
class JsonRecipeMapper {

    Recipe toRecipe(final RecipeDto representation) throws UnknownUnitException {
        final Recipe.Builder builder = new Recipe.Builder()
            .setId(representation.getId())
            .setTitle(representation.getTitle())
            .isADessert(representation.getDessert())
            .isHot(representation.getHot())
            .setPreparationTime(representation.getPreparationTime())
            .setCookingTime(representation.getCookingTime())
            .setServings(representation.getServings())
            .setSource(representation.getSource());

        for (final IngredientDto ingredientRepr : representation.getIngredients()) {
            final Ingredient ingredient = this.toIngredient(ingredientRepr);
            builder.setIngredient(ingredient);
        }

        return builder.build();
    }

    RecipeDto fromRecipe(final Recipe recipe) {
        final RecipeDto.Builder builder = new RecipeDto.Builder()
            .setId(recipe.getId())
            .setTitle(recipe.getTitle())
            .isADessert(recipe.getDessert())
            .isHot(recipe.getHot())
            .setPreparationTime(recipe.getPreparationTime())
            .setCookingTime(recipe.getCookingTime())
            .setServings(recipe.getServings())
            .setSource(recipe.getSource());

        for (final Ingredient ingredient : recipe.getIngredients()) {
            final IngredientDto dto = this.fromIngredient(ingredient);
            builder.setIngredient(dto);
        }

        return builder.build();
    }

    private Ingredient toIngredient(final IngredientDto representation) throws UnknownUnitException {
        final String unit = representation.getUnit().orElse(null);
        final Integer amount = representation.getAmount().orElse(null);
        return new Ingredient(representation.getName(), amount, unit);
    }

    private IngredientDto fromIngredient(final Ingredient ingredient) {
        final Integer amount = ingredient.getAmount().orElse(null);
        final String unit = ingredient.getUnit().map(Enum::name).orElse(null);
        return new IngredientDto(ingredient.getName(), amount, unit);
    }
}
