package fr.ggautier.recettes.spi.es;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
class EsIngredient {

    private final String name;

    private final Integer amount;

    private final String unit;

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
