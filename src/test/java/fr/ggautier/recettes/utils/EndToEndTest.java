package fr.ggautier.recettes.utils;

import fr.ggautier.recettes.spi.db.DbRecipe;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
@Tag("end-to-end")
public abstract class EndToEndTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected TestEntityManager entityManager;

    protected List<DbRecipe> getAllRecipes() {
        final EntityManager entityManager = this.entityManager.getEntityManager();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<DbRecipe> criteria = criteriaBuilder.createQuery(DbRecipe.class);
        final Root<DbRecipe> root = criteria.from(DbRecipe.class);
        final CriteriaQuery<DbRecipe> all = criteria.select(root);
        TypedQuery<DbRecipe> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    protected void store(final DbRecipe... recipes) {
        Arrays.stream(recipes).forEach(this.entityManager::persist);
        this.entityManager.flush();
    }
}
