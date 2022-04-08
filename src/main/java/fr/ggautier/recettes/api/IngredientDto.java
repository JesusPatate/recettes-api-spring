package fr.ggautier.recettes.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ggautier.arch.annotations.rest.Representation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Representation
public record IngredientDto(
    @NotBlank @JsonProperty String name,
    @Min(1) @JsonProperty Integer amount,
    @JsonProperty String unit
) {

    public IngredientDto(
        @JsonProperty("name") final String name,
        @JsonProperty("amount") final Integer amount,
        @JsonProperty("unit") final String unit
    ) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }
}