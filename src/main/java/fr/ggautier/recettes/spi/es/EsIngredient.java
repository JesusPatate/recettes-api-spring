package fr.ggautier.recettes.spi.es;

import com.fasterxml.jackson.annotation.JsonProperty;

record EsIngredient(String name, Integer amount, String unit) {

    EsIngredient(
        @JsonProperty("name") final String name,
        @JsonProperty("amount") final Integer amount,
        @JsonProperty("unit") final String unit
    ) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }
}
