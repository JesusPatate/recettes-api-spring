package fr.ggautier.recettes.api;

import fr.ggautier.recettes.domain.Ingredient;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.UnknownUnitException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class RecipeMapper {

    Recipe toRecipe(final InputRecipeDto recipeDto) throws UnknownUnitException {
        final UUID id = UUID.randomUUID();
        return this.toRecipe(recipeDto, id);
    }

    Recipe toRecipe(final InputRecipeDto recipeDto, final UUID id) throws UnknownUnitException {
        final Recipe.Builder builder = new Recipe.Builder()
            .setId(id)
            .setTitle(recipeDto.getTitle())
            .isADessert(recipeDto.getDessert())
            .isHot(recipeDto.getHot())
            .setPreparationTime(recipeDto.getPreparationTime())
            .setCookingTime(recipeDto.getCookingTime())
            .setServings(recipeDto.getServings())
            .setSource(recipeDto.getSource());

        for (final IngredientDto ingredientDto : recipeDto.getIngredients()) {
            final Ingredient ingredient = this.toIngredient(ingredientDto);
            builder.setIngredient(ingredient);
        }

        return builder.build();
    }

    OutputRecipeDto fromRecipe(final Recipe recipe) {
        final OutputRecipeDto.Builder builder = new OutputRecipeDto.Builder()
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

    private Ingredient toIngredient(final IngredientDto dto) throws UnknownUnitException {
        final String unit = dto.unit();
        final Integer amount = dto.amount();
        return new Ingredient(dto.name(), amount, unit);
    }

    private IngredientDto fromIngredient(final Ingredient ingredient) {
        final Integer amount = ingredient.getAmount().orElse(null);
        final String unit = ingredient.getUnit().map(Enum::name).orElse(null);
        return new IngredientDto(ingredient.getName(), amount, unit);
    }
}
