package fr.ggautier.recettes.api;

import fr.ggautier.arch.annotations.rest.Resource;
import fr.ggautier.recettes.domain.Recipe;
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
@RestController
@RequestMapping(RecipeController.ROUTE)
@CrossOrigin
public class RecipeController {

    static final String ROUTE = "/recipes";

    private final RecipesApiAdapter adapter;

    @Autowired
    public RecipeController(final RecipesApiAdapter adapter) {
        this.adapter = adapter;
    }

    @GetMapping
    public List<Recipe> getAll() {
        return this.adapter.getAll();
    }

    @GetMapping("/{id}")
    public OutputRecipeDto get(@PathVariable("id") final UUID id) {
        return this.adapter.get(id).orElse(null);
    }

    @PostMapping
    public OutputRecipeDto store(@RequestBody @Valid final InputRecipeDto recipe) throws Exception {
        final OutputRecipeDto outputDto = this.adapter.store(recipe);
        return outputDto;
    }

    @PutMapping("/{id}")
    public OutputRecipeDto update(
        @PathVariable("id") final UUID id,
        @RequestBody @Valid final InputRecipeDto recipe
    ) throws Exception {
        final OutputRecipeDto outputDto = this.adapter.update(id, recipe);
        return outputDto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final UUID id) throws RecipeNotFoundException {
        try {
            this.adapter.delete(id);
        } catch (final NoSuchElementException exception) {
            throw new RecipeNotFoundException();
        }
    }

    @PostMapping("/search")
    public List<OutputRecipeDto> search(@RequestParam(name = "value") final String term) throws Exception {
        return this.adapter.search(term);
    }
}