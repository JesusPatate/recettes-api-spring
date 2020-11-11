package fr.ggautier.recettes.e2e;

import fr.ggautier.recettes.spi.db.DbUnit;
import fr.ggautier.recettes.utils.EndToEndTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class GetAllUnits extends EndToEndTest {

    @Test
    void testGetAll() throws Exception {
        // Given
        final DbUnit unit1 = new DbUnit(UUID.randomUUID(), "unit1");
        final DbUnit unit2 = new DbUnit(UUID.randomUUID(), "unit2");
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
}
