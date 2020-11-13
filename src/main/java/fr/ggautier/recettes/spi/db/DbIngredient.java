package fr.ggautier.recettes.spi.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DbIngredient {

    private String name;

    private Integer amount;

    private String unit;
}
