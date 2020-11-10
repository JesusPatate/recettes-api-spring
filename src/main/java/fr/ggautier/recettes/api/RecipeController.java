package fr.ggautier.recettes.api;

import fr.ggautier.arch.annotations.Adapter;
import fr.ggautier.arch.annotations.rest.Resource;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.RecipeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Resource
@Adapter
@RestController
@RequestMapping(RecipeController.ROUTE)
public class RecipeController {

    static final String ROUTE = "/recipes";

    private final RecipeManager manager;

    @Autowired
    public RecipeController(final RecipeManager manager) {
        this.manager = manager;
    }

    @GetMapping
    public List<Recipe> getAll() {
        return this.manager.getAll();
    }

    @PutMapping("/{id}")
    public Recipe store(@PathVariable("id") final UUID id, @RequestBody @Valid final JsonRecipe json)
        throws NotMatchingIdentifiersException {

        if (!id.equals(json.getId())) {
            throw new NotMatchingIdentifiersException();
        }

        final Recipe recipe = new Recipe(id, json.getTitle());
        this.manager.store(recipe);
        return recipe;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final UUID id) throws RecipeNotFoundException {
        try {
            this.manager.delete(id);
        } catch (final NoSuchElementException exception) {
            throw new RecipeNotFoundException();
        }
    }
}