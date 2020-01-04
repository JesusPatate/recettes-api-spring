package fr.ggautier.recettes.spi;

import fr.ggautier.recettes.domain.Recipe;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DatabaseRecipeRepositoryTest {

    @TestConfiguration
    static class TestConfig {

        @Autowired
        private ZoneDAO dao;

        @Bean
        public DatabaseRecipeRepository repository() {
            return new DatabaseRecipeRepository(this.dao);
        }
    }

    @Autowired
    private DatabaseRecipeRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void getAll() {
        // Given
        final RecipeDbModel recipe1 = new RecipeDbModel(UUID.randomUUID(), "recipe1");
        final RecipeDbModel recipe2 = new RecipeDbModel(UUID.randomUUID(), "recipe2");
        this.entityManager.persist(recipe1);
        this.entityManager.persist(recipe2);
        this.entityManager.flush();

        // When
        final List<Recipe> result = this.repository.getAll();

        // Then
        assertThat(result).hasSize(2)
            .anyMatch(recipe -> recipe.getId().equals(recipe1.getId()) &&
                recipe.getTitle().equals(recipe1.getTitle()))
            .anyMatch(recipe -> recipe.getId().equals(recipe2.getId()) &&
                recipe.getTitle().equals(recipe2.getTitle()));
    }

    @Test
    void save() {
        // Given
        final Recipe recipe = new Recipe(UUID.randomUUID(), "recipe1");

        // When
        this.repository.save(recipe);

        // Then
        final RecipeDbModel dbModel = this.entityManager.find(RecipeDbModel.class, recipe.getId());

        assertThat(dbModel).isNotNull();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dbModel.getId()).isEqualTo(recipe.getId());
            softly.assertThat(dbModel.getTitle()).isEqualTo(recipe.getTitle());
        });
    }
}