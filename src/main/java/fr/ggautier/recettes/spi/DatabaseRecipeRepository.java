package fr.ggautier.recettes.spi;

import fr.ggautier.arch.annotations.Adapter;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.Recipes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Adapter
public class DatabaseRecipeRepository implements Recipes {

    private final ZoneDAO dao;

    @Autowired
    public DatabaseRecipeRepository(final ZoneDAO dao) {
        this.dao = dao;
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
    public void save(final Recipe recipe) {
        final RecipeDbModel dbModel = this.toDbModel(recipe);
        this.dao.save(dbModel);
    }

    /**
     * Builds a DB representation of a recipe.
     *
     * @param recipe The recipe to convert into a DB representation
     */
    private RecipeDbModel toDbModel(final Recipe recipe) {
        return new RecipeDbModel(recipe.getId(), recipe.getTitle());
    }

    /**
     * Reconstructs a recipe from its DB representation.
     *
     * @param dbModel The DB representation of the recipe to be reconstructed
     */
    private Recipe fromDbModel(final RecipeDbModel dbModel) {
        return new Recipe(dbModel.getId(), dbModel.getTitle());
    }
}
