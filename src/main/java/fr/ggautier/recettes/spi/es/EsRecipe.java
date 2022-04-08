package fr.ggautier.recettes.spi.es;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public record EsRecipe(
    String id,
    String title,
    Boolean hot,
    Boolean dessert,
    Integer servings,
    Set<EsIngredient> ingredients,
    Integer preparationTime,
    Integer cookingTime,
    String source
) {

    public static class Builder {

        private String id = null;
        private String title = null;
        private Boolean hot = null;
        private Boolean dessert = null;
        private Integer servings = null;
        private Integer preparationTime = null;
        private Integer cookingTime = null;
        private final Set<EsIngredient> ingredients = new HashSet<>();
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

        public Builder addIngredient(final EsIngredient ingredient) {
            this.ingredients.add(ingredient);
            return this;
        }

        public Builder setSource(final String source) {
            this.source = source;
            return this;
        }

        public EsRecipe build() {
            return new EsRecipe(
                this.id,
                this.title,
                this.hot,
                this.dessert,
                this.servings,
                this.ingredients,
                this.preparationTime,
                this.cookingTime,
                this.source
            );
        }
    }

    @JsonCreator
    public EsRecipe(
        @JsonProperty("id") final String id,
        @JsonProperty("title") final String title,
        @JsonProperty("hot") final Boolean hot,
        @JsonProperty("dessert") final Boolean dessert,
        @JsonProperty("servings") final Integer servings,
        @JsonProperty("ingredients") final Set<EsIngredient> ingredients,
        @JsonProperty("preparationTime") final Integer preparationTime,
        @JsonProperty("cookingTime") final Integer cookingTime,
        @JsonProperty("source") final String source
    ) {
        this.id = id;
        this.title = title;
        this.hot = hot;
        this.dessert = dessert;
        this.servings = servings;
        this.ingredients = ingredients;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.source = source;
    }
}
