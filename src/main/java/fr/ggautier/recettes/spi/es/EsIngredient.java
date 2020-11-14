package fr.ggautier.recettes.spi.es;

import lombok.Data;

@Data
public class EsIngredient {

    private final String name;

    private final Integer amount;

    private final String unit;
}
