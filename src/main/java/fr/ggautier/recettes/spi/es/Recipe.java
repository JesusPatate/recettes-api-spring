package fr.ggautier.recettes.spi.es;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Recipe {

    @Setter
    public static class Builder {

        private String id = null;
        private String title = null;
        private Boolean hot = null;
        private Boolean dessert = null;
        private Integer servings = null;
        private Integer preparationTime = null;
        private Integer cookingTime = null;
        private final Set<Ingredient> ingredients = new HashSet<>();
        private String source = null;

        public Builder setId(final String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }

        public Builder setHot(final Boolean hot) {
            this.hot = hot;
            return this;
        }

        public Builder setDessert(final Boolean dessert) {
            this.dessert = dessert;
            return this;
        }

        public Builder setServings(final Integer servings) {
            this.servings = servings;
            return this;
        }

        public Builder setPreparationTime(final Integer preparationTime) {
            this.preparationTime = preparationTime;
            return this;
        }

        public Builder setCookingTime(final Integer cookingTime) {
            this.cookingTime = cookingTime;
            return this;
        }

        public Builder addIngredient(final Ingredient ingredient) {
            this.ingredients.add(ingredient);
            return this;
        }

        public Builder setSource(final String source) {
            this.source = source;
            return this;
        }

        public Recipe build() {
            return Recipe.buildFrom(this);
        }
    }

    private final String id;

    private final String title;

    private final Boolean hot;

    private final Boolean dessert;

    private final Integer servings;

    private final Set<Ingredient> ingredients = new HashSet<>();

    private final Integer preparationTime;

    private final Integer cookingTime;

    private final String source;

    private Recipe(
        final String id,
        final String title,
        final Boolean hot,
        final Boolean dessert,
        final Integer servings,
        final Integer preparationTime,
        final Integer cookingTime,
        final String source
    ) {
        this.id = id;
        this.title = title;
        this.hot = hot;
        this.dessert = dessert;
        this.servings = servings;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.source = source;
    }

    private void addIngredient(final Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    private static Recipe buildFrom(final Builder builder) {
        final Recipe recipe = new Recipe(
            builder.id,
            builder.title,
            builder.hot,
            builder.dessert,
            builder.servings,
            builder.preparationTime,
            builder.cookingTime,
            builder.source
        );

        builder.ingredients.forEach(recipe::addIngredient);

        return recipe;
    }
}
