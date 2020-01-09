package fr.ggautier.recettes.spi;

import fr.ggautier.recettes.domain.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DatabaseUnitRepositoryTest {

    private DatabaseUnitRepository repository;

    @Autowired
    private UnitDAO dao;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        this.repository = new DatabaseUnitRepository(this.dao);
    }

    @Test
    void testGetAllNoUnit() {
        // Given

        // When
        final List<Unit> output = this.repository.getAll();

        // Then
        assertThat(output).isEmpty();
    }

    @Test
    void getAll() {
        // Given
        final UnitDbModel unit1 = new UnitDbModel(1L, "unit1");
        final UnitDbModel unit2 = new UnitDbModel(2L, "unit2");
        this.store(unit1, unit2);

        // When
        final List<Unit> output = this.repository.getAll();

        // Then
        assertThat(output).hasSize(2)
            .anyMatch(unit -> unit.getId().equals(unit1.getId()) &&
                unit.getName().equals(unit1.getName()))
            .anyMatch(unit -> unit.getId().equals(unit2.getId()) &&
                unit.getName().equals(unit2.getName()));
    }

    private void store(final UnitDbModel... units) {
        Arrays.stream(units).forEach(this.entityManager::persist);
        this.entityManager.flush();
    }
}