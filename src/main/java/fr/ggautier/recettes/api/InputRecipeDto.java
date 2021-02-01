package fr.ggautier.recettes.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ggautier.arch.annotations.rest.Representation;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Representation
@Data
public class InputRecipeDto {

    @NotBlank
    private final String title;

    private final Boolean hot;

    private final Boolean dessert;

    private final Integer servings;

    private final Integer preparationTime;

    private final Integer cookingTime;

    @Valid
    @NotEmpty
    private final List<IngredientDto> ingredients = new ArrayList<>();

    private final String source;

    // Used by Jackson.
    @SuppressWarnings("unused")
    public InputRecipeDto(
        @JsonProperty("title") final String title,
        @JsonProperty("hot") final Boolean hot,
        @JsonProperty("dessert") final Boolean dessert,
        @JsonProperty("servings") final Integer servings,
        @JsonProperty("preparationTime") final Integer preparationTime,
        @JsonProperty("cookingTime") final Integer cookingTime,
        @JsonProperty("ingredients") final List<IngredientDto> ingredients,
        @JsonProperty("source") final String source
    ) {
        this.title = title;
        this.hot = hot;
        this.dessert = dessert;
        this.servings = servings;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.source = source;

        if (ingredients != null) {
            this.ingredients.addAll(ingredients);
        }
    }

    private InputRecipeDto(final Builder builder) {
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

        private String title = null;
        private Boolean hot = null;
        private Boolean dessert = null;
        private Integer preparationTime = null;
        private Integer cookingTime = null;
        private Integer servings = null;
        private String source = null;
        private final List<IngredientDto> ingredients = new ArrayList<>();

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
        public InputRecipeDto build() {
            return new InputRecipeDto(this);
        }
    }
}
