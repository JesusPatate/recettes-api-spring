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
@Table(name = "unit")
public class UnitDbModel {

    /**
     * Unit's identifier.
     */
    @Id
    UUID id;

    /**
     * Unit's name.
     */
    String name;
}
