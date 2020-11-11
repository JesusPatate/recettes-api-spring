package fr.ggautier.recettes.spi.db;

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
public class DbUnit {

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
