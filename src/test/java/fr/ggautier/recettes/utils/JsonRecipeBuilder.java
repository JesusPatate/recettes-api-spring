package fr.ggautier.recettes.utils;

import fr.ggautier.recettes.api.JsonIngredient;
import fr.ggautier.recettes.api.JsonRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonRecipeBuilder {

    private UUID id = null;

    private String title = null;

    private List<JsonIngredient> ingredients = new ArrayList<>();

    public JsonRecipeBuilder setId(final UUID id) {
        this.id = id;
        return this;
    }

    public JsonRecipeBuilder setTitle(final String title) {
        this.title = title;
        return this;
    }

    public JsonRecipeBuilder addIngredient(final JsonIngredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public JsonRecipe build() {
        return new JsonRecipe(this.id, this.title, this.ingredients);
    }
}
