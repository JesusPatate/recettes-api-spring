package fr.ggautier.recettes.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.ggautier.arch.annotations.rest.Representation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Representation
public class JsonIngredient {

    @NotBlank
    private final String name;

    @Min(1)
    @JsonProperty
    private final Integer amount;

    @JsonProperty("unit")
    private final UUID unitId;

    public JsonIngredient(
        @JsonProperty("name") final String name,
        @JsonProperty("amount") final Integer amount,
        @JsonProperty("unit") final UUID unitId
    ) {
        this.name = name;
        this.amount = amount;
        this.unitId = unitId;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public Optional<Integer> getAmount() {
        return Optional.ofNullable(amount);
    }

    @JsonIgnore
    public Optional<UUID> getUnitId() {
        return Optional.ofNullable(unitId);
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

        return name.equals(other.name) &&
            Objects.equals(amount, other.amount) &&
            Objects.equals(unitId, other.unitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount, unitId);
    }
}
