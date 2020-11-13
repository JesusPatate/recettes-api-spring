package fr.ggautier.recettes.spi.es;

import lombok.Getter;

@Getter
public class EsIngredient {

    private final String name;

    private final Integer amount;

    private final String unit;

    public EsIngredient(final String name) {
        this.name = name;
        this.amount = null;
        this.unit = null;
    }

    public EsIngredient(final String name, final int amount) {
        this.name = name;
        this.amount = amount;
        this.unit = null;
    }
}
