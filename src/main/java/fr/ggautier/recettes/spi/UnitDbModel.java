package fr.ggautier.recettes.spi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

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
    Long id;

    /**
     * Unit's name.
     */
    String name;

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }

        final UnitDbModel other = (UnitDbModel) object;

        return id.equals(other.id) && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
