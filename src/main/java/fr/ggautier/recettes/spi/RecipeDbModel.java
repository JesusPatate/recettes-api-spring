package fr.ggautier.recettes.spi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipe")
public class RecipeDbModel {

    /**
     * Recipe's identifier.
     */
    @Id
    private UUID id;

    /**
     * Recipe's title.
     */
    private String title;
}
