package fr.ggautier.recettes.spi.es;

import lombok.Getter;

@Getter
public class Ingredient {

    private final String name;

    private final Integer amount;

    private final String unitId;

    public Ingredient(final String name) {
        this.name = name;
        this.amount = null;
        this.unitId = null;
    }

    public Ingredient(final String name, final int amount) {
        this.name = name;
        this.amount = amount;
        this.unitId = null;
    }
}
