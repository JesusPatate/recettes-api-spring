package fr.ggautier.recettes.api;

import fr.ggautier.recettes.domain.Ingredient;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.UnknownUnitException;
import org.springframework.stereotype.Component;

@Component
class JsonRecipeMapper {

    Recipe toRecipe(final JsonRecipe representation) throws UnknownUnitException {
        final Recipe.Builder builder = new Recipe.Builder()
            .setId(representation.getId())
            .setTitle(representation.getTitle())
            .isADessert(representation.getDessert())
            .isHot(representation.getHot())
            .setPreparationTime(representation.getPreparationTime())
            .setCookingTime(representation.getCookingTime())
            .setServings(representation.getServings())
            .setSource(representation.getSource());

        for (final JsonIngredient ingredientRepr : representation.getIngredients()) {
            final Ingredient ingredient = this.toIngredient(ingredientRepr);
            builder.setIngredient(ingredient);
        }

        return builder.build();
    }

    private Ingredient toIngredient(final JsonIngredient representation) throws UnknownUnitException {
        final String unit = representation.getUnit().orElse(null);
        final Integer amount = representation.getAmount().orElse(null);
        return new Ingredient(representation.getName(), amount, unit);
    }
}
