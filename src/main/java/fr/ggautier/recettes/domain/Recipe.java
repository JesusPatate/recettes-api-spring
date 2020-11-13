package fr.ggautier.recettes.domain;

import fr.ggautier.arch.annotations.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recipe {

    @EqualsAndHashCode.Include
    private final UUID id;

    private String title;

    private Boolean hot;

    private Boolean dessert;

    private Integer preparationTime;

    private Integer cookingTime;

    private Integer servings;

    private String source;

    private List<Ingredient> ingredients = new ArrayList<>();

    private Recipe(final Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.hot = builder.hot;
        this.dessert = builder.dessert;
        this.preparationTime = builder.preparationTime;
        this.cookingTime = builder.cookingTime;
        this.servings = builder.servings;
        this.source = builder.source;
        this.ingredients.addAll(builder.ingredients);
    }

    public static class Builder {

        private UUID id = null;
        private String title = null;
        private Boolean hot = null;
        private Boolean dessert = null;
        private Integer preparationTime = null;
        private Integer cookingTime = null;
        private Integer servings = null;
        private String source = null;
        private List<Ingredient> ingredients = new ArrayList<>();

        /**
         * Sets the identifier of the recipe.
         *
         * @param id The unique identifier of the recipe
         * @return The builder
         */
        public Builder setId(final UUID id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name of the recipe.
         *
         * @param title The name of the recipe
         * @return The builder
         */
        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets whether the recipe is for a hot dish.
         *
         * @param hot Whether the recipe allows to make a hot dish
         * @return The builder
         */
        public Builder isHot(final boolean hot) {
            this.hot = hot;
            return this;
        }

        /**
         * Sets whether the recipe is for a dessert.
         *
         * @param dessert Whether the recipe allows to make a dessert
         * @return The builder
         */
        public Builder isADessert(final boolean dessert) {
            this.dessert = dessert;
            return this;
        }

        /**
         * Sets the preparation time needed to make the recipe.
         *
         * @param preparationTime The preparation time required by the recipe
         * @return The builder
         */
        public Builder setPreparationTime(final int preparationTime) {
            this.preparationTime = preparationTime;
            return this;
        }

        /**
         * Sets the cooking time needed to make the recipe.
         *
         * @param cookingTime The cooking time required by the recipe
         * @return The builder
         */
        public Builder setCookingTime(final int cookingTime) {
            this.cookingTime = cookingTime;
            return this;
        }

        /**
         * Sets the number of servings the recipe yields.
         *
         * @param servings The number of servings the recipe yields
         * @return The builder
         */
        public Builder setServings(final Integer servings) {
            this.servings = servings;
            return this;
        }

        /**
         * Sets the source from which the recipe was taken.
         *
         * @param source A title of a book, a URL of a website...
         * @return The builder
         */
        public Builder setSource(final String source) {
            this.source = source;
            return this;
        }

        /**
         * Adds an ingredient required in the recipe.
         *
         * @param ingredient An ingredient required in the recipe
         * @return The builder
         */
        public synchronized Builder setIngredient(final Ingredient ingredient) {
            this.ingredients.add(ingredient);
            return this;
        }

        /**
         * Returns the new instance.
         */
        public Recipe build() {
            return new Recipe(this);
        }
    }
}
