package fr.ggautier.recettes.spi;

import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.utils.IntegrationTest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RecipeRepositoryTest implements IntegrationTest {

    private RecipeRepository repository;

    @Autowired
    private RecipeDAO dao;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        this.repository = new RecipeRepository(this.dao);
    }

    @Test
    void testGetAll() {
        // Given
        final RecipeDbModel recipe1 = new RecipeDbModel(UUID.randomUUID(), "recipe1");
        final RecipeDbModel recipe2 = new RecipeDbModel(UUID.randomUUID(), "recipe2");
        this.store(recipe1, recipe2);

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
    void testGet() {
        // Given
        final RecipeDbModel recipe = new RecipeDbModel(UUID.randomUUID(), "recipe1");
        this.store(recipe);

        // When
        final Optional<Recipe> result = this.repository.get(recipe.getId());

        // Then
        final Recipe expected = new Recipe(recipe.getId(), recipe.getTitle());

        assertThat(result).contains(expected);
    }

    @Test
    void testAdd() {
        // Given
        final Recipe recipe = new Recipe(UUID.randomUUID(), "recipe1");

        // When
        this.repository.add(recipe);

        // Then
        final RecipeDbModel dbModel = this.entityManager.find(RecipeDbModel.class, recipe.getId());

        assertThat(dbModel).isNotNull();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dbModel.getId()).isEqualTo(recipe.getId());
            softly.assertThat(dbModel.getTitle()).isEqualTo(recipe.getTitle());
        });
    }

    @Test
    void testRemove() {
        // Given
        final RecipeDbModel recipe1 = new RecipeDbModel(UUID.randomUUID(), "recipe1");
        final RecipeDbModel recipe2 = new RecipeDbModel(UUID.randomUUID(), "recipe2");
        this.store(recipe1, recipe2);

        // When
        final Recipe recipe = new Recipe(recipe1.getId(), recipe1.getTitle());
        this.repository.remove(recipe);

        // Then
        final RecipeDbModel dbModel1 = this.entityManager.find(RecipeDbModel.class, recipe1.getId());

        assertThat(dbModel1).isNull();

        final RecipeDbModel dbModel2 = this.entityManager.find(RecipeDbModel.class, recipe2.getId());

        assertThat(dbModel2).isEqualTo(recipe2);
    }

    private void store(final RecipeDbModel... recipes) {
        Arrays.stream(recipes).forEach(this.entityManager::persist);
        this.entityManager.flush();
    }
}