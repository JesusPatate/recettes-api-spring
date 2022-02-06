package fr.ggautier.recettes.spi;

import fr.ggautier.arch.annotations.Adapter;
import fr.ggautier.recettes.domain.IStoreRecipes;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.UnknownUnitException;
import fr.ggautier.recettes.spi.db.DbRecipe;
import fr.ggautier.recettes.spi.db.DbRecipeMapper;
import fr.ggautier.recettes.spi.db.RecipeDAO;
import fr.ggautier.recettes.spi.es.EsClient;
import fr.ggautier.recettes.spi.es.EsRecipe;
import fr.ggautier.recettes.spi.es.EsRecipeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Adapter
public class RecipeRepository implements IStoreRecipes {

    private final static Logger LOG = LoggerFactory.getLogger(RecipeRepository.class);

    private final RecipeDAO dao;

    private final EsClient esClient;

    private final EsRecipeMapper esRecipeMapper;

    private final DbRecipeMapper dbModelMapper;

    @Autowired
    public RecipeRepository(
        final RecipeDAO dao,
        final EsClient esClient,
        final EsRecipeMapper esRecipeMapper,
        final DbRecipeMapper dbModelMapper
    ) {
        this.dao = dao;
        this.esClient = esClient;
        this.esRecipeMapper = esRecipeMapper;
        this.dbModelMapper = dbModelMapper;
    }

    @Override
    public List<Recipe> getAll() {
        final List<Recipe> recipes = new ArrayList<>();

        this.dao.findAll().forEach(dbModel -> {
            final Recipe recipe = this.fromDbModel(dbModel);
            recipes.add(recipe);
        });

        return recipes;
    }

    @Override
    public Optional<Recipe> get(final UUID id) {
        return this.dao.findById(id).map(this::fromDbModel);
    }

    @Override
    public List<Recipe> search(final String term) throws Exception {
        final List<EsRecipe> results = this.esClient.searchRecipe(term);
        final List<Recipe> recipes = new ArrayList<>();

        for (final EsRecipe result : results) {
            final Recipe recipe = this.esRecipeMapper.toRecipe(result);
            recipes.add(recipe);
        }

        return recipes;
    }

    @Override
    public void add(final Recipe recipe) {
        final DbRecipe dbModel = this.dbModelMapper.toDbModel(recipe);
        this.dao.save(dbModel);

        try {
            final EsRecipe esModel = this.esRecipeMapper.toEsRecipe(recipe);
            this.esClient.index(esModel);
        } catch (final Exception exception) {
            LOG.error("Failed to index recipe ({})", recipe, exception);
        }
    }

    @Override
    public void remove(final Recipe recipe) {
        final DbRecipe dbModel = this.dbModelMapper.toDbModel(recipe);
        this.dao.delete(dbModel);

        try {
            this.esClient.delete(recipe.getId());
        } catch (final Exception exception) {
            LOG.error("Failed to remove recipe from ES index ({})", recipe, exception);
        }
    }

    /**
     * Reconstructs a recipe from its database representation.
     *
     * @param dbModel Database model of the recipe
     */
    private Recipe fromDbModel(final DbRecipe dbModel) {
        try {
            return this.dbModelMapper.fromDbModel(dbModel);
        } catch (final UnknownUnitException exception) {
            throw new RuntimeException(exception);
        }
    }
}
