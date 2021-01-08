package fr.ggautier.recettes.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ggautier.arch.annotations.rest.Representation;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Representation
@EqualsAndHashCode
public class IngredientDto {

    @NotBlank
    @JsonProperty
    private final String name;

    @Min(1)
    @JsonProperty
    private final Integer amount;

    @JsonProperty
    private final String unit;

    public IngredientDto(
        @JsonProperty("name") final String name,
        @JsonProperty("amount") final Integer amount,
        @JsonProperty("unit") final String unit
    ) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public String getName() {
        return this.name;
    }

    @JsonIgnore
    public Optional<Integer> getAmount() {
        return Optional.ofNullable(this.amount);
    }

    @JsonIgnore
    public Optional<String> getUnit() {
        return Optional.ofNullable(this.unit);
    }
}