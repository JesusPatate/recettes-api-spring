package fr.ggautier.recettes.api;

import fr.ggautier.arch.annotations.Adapter;
import fr.ggautier.arch.annotations.rest.Resource;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.RecipeFinder;
import fr.ggautier.recettes.domain.RecipeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Resource
@Adapter
@RestController
@RequestMapping(RecipeController.ROUTE)
@CrossOrigin
public class RecipeController {

    static final String ROUTE = "/recipes";

    private final RecipeManager manager;

    private final RecipeFinder finder;

    private final JsonRecipeMapper jsonRecipeMapper;

    @Autowired
    public RecipeController(
        final RecipeManager manager,
        final RecipeFinder finder,
        final JsonRecipeMapper jsonRecipeMapper
    ) {
        this.manager = manager;
        this.finder = finder;
        this.jsonRecipeMapper = jsonRecipeMapper;
    }

    @GetMapping
    public List<Recipe> getAll() {
        return this.manager.getAll();
    }

    @PutMapping
    public Recipe store(@RequestBody @Valid final JsonRecipe json) throws Exception {
        final Recipe recipe = this.jsonRecipeMapper.toRecipe(json);
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

    @PostMapping("/search")
    public List<Recipe> search(@RequestParam(name = "value") final String term) throws Exception {
        return this.finder.search(term);
    }
}