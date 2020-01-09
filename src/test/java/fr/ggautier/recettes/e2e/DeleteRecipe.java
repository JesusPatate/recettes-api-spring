package fr.ggautier.recettes.e2e;

import fr.ggautier.recettes.spi.RecipeDbModel;
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

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
class DeleteRecipe {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testDelete() throws Exception {
        // Given
        final RecipeDbModel recipe1 = new RecipeDbModel(UUID.randomUUID(), "recipe1");
        final RecipeDbModel recipe2 = new RecipeDbModel(UUID.randomUUID(), "recipe2");
        this.storeRecipes(recipe1, recipe2);

        // When
        final ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/recipes/{id}", recipe1.getId()));

        // Then
        actions.andExpect(MockMvcResultMatchers.status().isOk());

        final List<RecipeDbModel> recipes = this.getAllRecipes();

        assertThat(recipes).containsExactly(recipe2);
    }

    private void storeRecipes(final RecipeDbModel... recipes) {
        Arrays.stream(recipes).forEach(this.entityManager::persist);
        this.entityManager.flush();
    }

    private List<RecipeDbModel> getAllRecipes() {
        final EntityManager entityManager = this.entityManager.getEntityManager();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<RecipeDbModel> criteria = criteriaBuilder.createQuery(RecipeDbModel.class);
        final Root<RecipeDbModel> root = criteria.from(RecipeDbModel.class);
        final CriteriaQuery<RecipeDbModel> all = criteria.select(root);
        TypedQuery<RecipeDbModel> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }
}
