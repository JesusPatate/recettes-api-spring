package fr.ggautier.recettes.spi.es;

import fr.ggautier.recettes.domain.Ingredient;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.Unit;
import fr.ggautier.recettes.domain.UnknownUnitException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EsRecipeMapper {

    public Recipe toRecipe(final EsRecipe esRecipe) {
        final Recipe.Builder builder = new Recipe.Builder()
            .setId(UUID.fromString(esRecipe.id()))
            .setTitle(esRecipe.title())
            .isHot(esRecipe.hot())
            .isADessert(esRecipe.dessert())
            .setPreparationTime(esRecipe.preparationTime())
            .setCookingTime(esRecipe.cookingTime())
            .setServings(esRecipe.servings())
            .setSource(esRecipe.source());

        for (EsIngredient i : esRecipe.ingredients()) {
            final Ingredient ingredient = this.toIngredient(i);
            builder.setIngredient(ingredient);
        }

        return builder.build();
    }

    public EsRecipe toEsRecipe(final Recipe recipe) {
        final EsRecipe.Builder builder = new EsRecipe.Builder()
            .setId(recipe.getId().toString())
            .setTitle(recipe.getTitle())
            .setHot(recipe.getHot())
            .setDessert(recipe.getDessert())
            .setPreparationTime(recipe.getPreparationTime())
            .setCookingTime(recipe.getCookingTime())
            .setServings(recipe.getServings())
            .setSource(recipe.getSource());

        recipe.getIngredients().stream()
            .map(this::toIngredient)
            .forEach(builder::addIngredient);

        return builder.build();
    }

    private Ingredient toIngredient(EsIngredient i) {
        try {
                return new Ingredient(i.name(), i.amount(), i.unit());
        } catch (final UnknownUnitException exception) {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }

    private EsIngredient toIngredient(final Ingredient ingredient) {
        final Integer amount = ingredient.getAmount().orElse(null);
        final String unit = ingredient.getUnit().map(Unit::name).orElse(null);
        return new EsIngredient(ingredient.getName(), amount, unit);
    }
}
