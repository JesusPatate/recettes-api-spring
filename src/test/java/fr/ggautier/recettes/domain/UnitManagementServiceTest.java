package fr.ggautier.recettes.domain;

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

class UnitManagementServiceTest {

    private UnitManagementService service;

    @Mock
    private Units repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        this.service = new UnitManagementService(this.repository);
    }

    @Test
    void testGetAllNoUnit() {
        given(this.repository.getAll()).willReturn(Collections.emptyList());

        // When
        final List<Unit> result = this.service.getAll();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void getAll() {
        // Given
        final Unit unit1 = new Unit(UUID.randomUUID(), "unit1");
        final Unit unit2 = new Unit(UUID.randomUUID(), "unit2");

        final List<Unit> units = new ArrayList<>();
        units.add(unit1);
        units.add(unit2);

        given(this.repository.getAll()).willReturn(units);

        // When
        final List<Unit> output = this.service.getAll();

        // Then
        assertThat(output).containsExactly(unit1, unit2);
    }
}