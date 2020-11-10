package fr.ggautier.recettes.utils;

import fr.ggautier.recettes.spi.RecipeDbModel;
import fr.ggautier.recettes.spi.UnitDbModel;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
@Tag("end-to-end")
public abstract class EndToEndTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected TestEntityManager entityManager;

    protected List<RecipeDbModel> getAllRecipes() {
        final EntityManager entityManager = this.entityManager.getEntityManager();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<RecipeDbModel> criteria = criteriaBuilder.createQuery(RecipeDbModel.class);
        final Root<RecipeDbModel> root = criteria.from(RecipeDbModel.class);
        final CriteriaQuery<RecipeDbModel> all = criteria.select(root);
        TypedQuery<RecipeDbModel> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    protected void store(final RecipeDbModel... recipes) {
        Arrays.stream(recipes).forEach(this.entityManager::persist);
        this.entityManager.flush();
    }

    protected void store(final UnitDbModel... recipes) {
        Arrays.stream(recipes).forEach(this.entityManager::persist);
        this.entityManager.flush();
    }
}
