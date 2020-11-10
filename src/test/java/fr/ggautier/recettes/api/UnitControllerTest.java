package fr.ggautier.recettes.api;

import fr.ggautier.recettes.domain.Unit;
import fr.ggautier.recettes.domain.UnitManager;
import fr.ggautier.recettes.utils.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class UnitControllerTest implements IntegrationTest {

    private UnitController controller;

    @Mock
    private UnitManager manager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.controller = new UnitController(manager);
    }

    /**
     * When no unit is saved, {@link UnitController} should return an empty list to GET requests to /units.
     */
    @Test
    void testGetAllNoUnit() {
        // Given
        given(this.manager.getAll()).willReturn(Collections.emptyList());

        // When
        final List<Unit> units = this.controller.getAll();

        // Then
        assertThat(units).isEmpty();
    }

    @Test
    void testGetAll() {
        // Given
        final Unit unit1 = new Unit(UUID.randomUUID(), "unit1");
        final Unit unit2 = new Unit(UUID.randomUUID(), "unit2");

        final List<Unit> units = new ArrayList<>();
        units.add(unit1);
        units.add(unit2);

        given(this.manager.getAll()).willReturn(units);

        // When
        final List<Unit> output = this.controller.getAll();

        // Then
        assertThat(output).containsExactly(unit1, unit2);
    }
}
