package fr.ggautier.recettes.domain;


import fr.ggautier.arch.annotations.Entity;
import lombok.Data;

import java.util.Optional;

@Entity
@Data
public class Ingredient {

    private String name;

    private Integer amount;

    private Unit unit;

    public Ingredient(String name, Integer amount, String unit) throws UnknownUnitException {
        this.name = name;
        this.amount = amount;
        this.unit = getUnit(unit);
    }

    public Optional<Integer> getAmount() {
        return Optional.ofNullable(this.amount);
    }

    public Optional<Unit> getUnit() {
        return Optional.ofNullable(this.unit);
    }

    private static Unit getUnit(final String unit) throws UnknownUnitException {
        Unit result = null;

        if (unit != null) {
            try {
                result = Unit.valueOf(unit);
            } catch (final IllegalArgumentException exception) {
                throw new UnknownUnitException(unit);
            }
        }

        return result;
    }
}
