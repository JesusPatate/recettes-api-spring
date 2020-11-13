package fr.ggautier.recettes.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ggautier.arch.annotations.rest.Representation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Optional;

@Representation
public class JsonIngredient {

    @NotBlank
    @JsonProperty
    private final String name;

    @Min(1)
    @JsonProperty
    private final Integer amount;

    @JsonProperty
    private final String unit;

    public JsonIngredient(
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

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }

        final JsonIngredient other = (JsonIngredient) object;

        return this.name.equals(other.name) &&
            Objects.equals(this.amount, other.amount) &&
            Objects.equals(this.unit, other.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.amount, this.unit);
    }
}
