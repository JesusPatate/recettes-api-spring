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

    public JsonRecipeBuilder addIngredient(final String name) {
        this.ingredients.add(new JsonIngredient(name, null, null));
        return this;
    }

    public JsonRecipeBuilder addIngredient(final String name, final int amount) {
        this.ingredients.add(new JsonIngredient(name, amount, null));
        return this;
    }

    JsonRecipeBuilder addIngredient(final String name, final int amount, final UUID unitId) {
        this.ingredients.add(new JsonIngredient(name, amount, unitId));
        return this;
    }

    public JsonRecipe build() {
        return new JsonRecipe(this.id, this.title, this.ingredients);
    }
}
