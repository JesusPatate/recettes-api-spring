package fr.ggautier.recettes.spi.es;

import fr.ggautier.recettes.domain.Ingredient;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.UnknownUnitException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class EsRecipeMapper {

    public Recipe toRecipe(final EsRecipe esRecipe) throws UnknownUnitException {
        final Recipe.Builder builder = new Recipe.Builder()
            .setId(UUID.fromString(esRecipe.getId()))
            .setTitle(esRecipe.getTitle())
            .isHot(esRecipe.getHot())
            .isADessert(esRecipe.getDessert())
            .setPreparationTime(esRecipe.getPreparationTime())
            .setCookingTime(esRecipe.getCookingTime())
            .setServings(esRecipe.getServings())
            .setSource(esRecipe.getSource());

        for (EsIngredient i : esRecipe.getIngredients()) {
            final Ingredient ingredient = this.toIngredient(i);
            builder.setIngredient(ingredient);
        }

        return builder.build();
    }

    private Ingredient toIngredient(final EsIngredient esIngredient) throws UnknownUnitException {
        final String unit = Optional.ofNullable(esIngredient.getUnit()).orElse(null);
        return new Ingredient(esIngredient.getName(), esIngredient.getAmount(), unit);
    }
}
