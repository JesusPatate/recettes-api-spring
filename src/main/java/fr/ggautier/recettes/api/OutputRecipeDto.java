package fr.ggautier.recettes.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ggautier.arch.annotations.rest.Representation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Representation
public class OutputRecipeDto {

    private final UUID id;

    private final InputRecipeDto recipe;

    // Used by Jackson.
    @SuppressWarnings("unused")
    public OutputRecipeDto(
        @JsonProperty("id") final UUID id,
        @JsonProperty("title") final String title,
        @JsonProperty("hot") final Boolean hot,
        @JsonProperty("dessert") final Boolean dessert,
        @JsonProperty("servings") final Integer servings,
        @JsonProperty("preparationTime") final Integer preparationTime,
        @JsonProperty("cookingTime") final Integer cookingTime,
        @JsonProperty("ingredients") final List<IngredientDto> ingredients,
        @JsonProperty("source") final String source
    ) {
        this.id = id;
        this.recipe = new InputRecipeDto(
            title,
            hot,
            dessert,
            servings,
            preparationTime,
            cookingTime,
            ingredients,
            source
        );
    }

    private OutputRecipeDto(final Builder builder) {
        this.id = builder.id;
        this.recipe = new InputRecipeDto(
            builder.title,
            builder.hot,
            builder.dessert,
            builder.servings,
            builder.preparationTime,
            builder.cookingTime,
            builder.ingredients,
            builder.source
        );
    }

    public UUID getId() {
        return this.id;
    }

    public String getTitle() {
        return this.recipe.getTitle();
    }

    public boolean getHot() {
        return this.recipe.getHot();
    }

    public boolean getDessert() {
        return this.recipe.getDessert();
    }

    public int getServings() {
        return this.recipe.getServings();
    }

    public int getPreparationTime() {
        return this.recipe.getPreparationTime();
    }

    public int getCookingTime() {
        return this.recipe.getCookingTime();
    }

    public String getSource() {
        return this.recipe.getSource();
    }

    public List<IngredientDto> getIngredients() {
        return this.recipe.getIngredients();
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
        private final List<IngredientDto> ingredients = new ArrayList<>();

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
        public synchronized Builder setIngredient(final IngredientDto ingredient) {
            this.ingredients.add(ingredient);
            return this;
        }

        /**
         * Returns the new instance.
         */
        public OutputRecipeDto build() {
            return new OutputRecipeDto(this);
        }
    }
}
