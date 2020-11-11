package fr.ggautier.recettes.spi;

import fr.ggautier.arch.annotations.Adapter;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.Recipes;
import fr.ggautier.recettes.spi.db.DbRecipe;
import fr.ggautier.recettes.spi.db.DbRecipeMapper;
import fr.ggautier.recettes.spi.db.RecipeDAO;
import fr.ggautier.recettes.spi.es.EsClient;
import fr.ggautier.recettes.spi.es.EsRecipe;
import fr.ggautier.recettes.spi.es.EsRecipeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Adapter
public class RecipeRepository implements Recipes {

    private final RecipeDAO dao;

    private final EsClient esClient;

    private final EsRecipeMapper esRecipeMapper;
    private final DbRecipeMapper dbModelMapper = new DbRecipeMapper();

    @Autowired
    public RecipeRepository(final RecipeDAO dao, final EsClient esClient, final EsRecipeMapper esRecipeMapper) {
        this.dao = dao;
        this.esClient = esClient;
        this.esRecipeMapper = esRecipeMapper;
    }

    @Override
    public List<Recipe> getAll() {
        final List<Recipe> recipes = new ArrayList<>();

        this.dao.findAll().forEach(dbModel -> {
            final Recipe recipe = dbModelMapper.fromDbModel(dbModel);
            recipes.add(recipe);
        });

        return recipes;
    }

    @Override
    public Optional<Recipe> get(final UUID id) {
        return this.dao.findById(id)
            .map(this.dbModelMapper::fromDbModel);
    }

    @Override
    public List<Recipe> search(String term) throws IOException {
        final List<EsRecipe> results = this.esClient.searchRecipe(term);
        final List<Recipe> recipes = new ArrayList<>();

        results.forEach(result -> {
            final Recipe recipe = this.esRecipeMapper.toRecipe(result);
            recipes.add(recipe);
        });

        return recipes;
    }

    @Override
    public void add(final Recipe recipe) {
        final DbRecipe dbModel = dbModelMapper.toDbModel(recipe);
        this.dao.save(dbModel);
    }

    @Override
    public void remove(final Recipe recipe) {
        final DbRecipe dbModel = dbModelMapper.toDbModel(recipe);
        this.dao.delete(dbModel);
    }

    /**
     * Builds a DB representation of a recipe.
     *
     * @param recipe The recipe to convert into a DB representation
     */
    private DbRecipe toDbModel(final Recipe recipe) {
        return dbModelMapper.toDbModel(recipe);
    }

    /**
     * Reconstructs a recipe from its DB representation.
     *
     * @param dbModel The DB representation of the recipe to be reconstructed
     */
    private Recipe fromDbModel(final DbRecipe dbModel) {
        return dbModelMapper.fromDbModel(dbModel);
    }
}
