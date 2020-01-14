package fr.ggautier.recettes.e2e;

import fr.ggautier.recettes.spi.UnitDbModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
class GetAllUnits {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testGetAll() throws Exception {
        // Given
        final UnitDbModel unit1 = new UnitDbModel(UUID.randomUUID(), "unit1");
        final UnitDbModel unit2 = new UnitDbModel(UUID.randomUUID(), "unit2");
        this.store(unit1, unit2);

        // When
        final ResultActions actions = this.mvc.perform(MockMvcRequestBuilders.get("/units"));

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$[0].id").value(unit1.getId().toString()))
            .andExpect(jsonPath("$[0].name").value(unit1.getName()))
            .andExpect(jsonPath("$[1].id").value(unit2.getId().toString()))
            .andExpect(jsonPath("$[1].name").value(unit2.getName()));
    }

    private void store(final UnitDbModel... recipes) {
        Arrays.stream(recipes).forEach(this.entityManager::persist);
        this.entityManager.flush();
    }
}
