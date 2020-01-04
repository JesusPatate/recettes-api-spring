package fr.ggautier.recettes.api;

import fr.ggautier.arch.annotations.Adapter;
import fr.ggautier.arch.annotations.rest.Resource;
import fr.ggautier.recettes.domain.Recipe;
import fr.ggautier.recettes.domain.RecipeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Resource
@Adapter
@RestController
@RequestMapping(RecipeController.ROUTE)
public class RecipeController {

    static final String ROUTE = "/recipes";

    private final RecipeManagementService service;

    @Autowired
    public RecipeController(final RecipeManagementService service) {
        this.service = service;
    }

    @GetMapping
    public List<Recipe> getAll() {
        return this.service.getAll();
    }

    @PutMapping("/{id}")
    public Recipe store(@PathVariable("id") final UUID id, @RequestBody final JsonRecipe json) {
        if (!id.equals(json.getId())) {
            throw new NotMatchingIdentifiersException();
        }

        final Recipe recipe = new Recipe(id, json.getTitle());
        this.service.store(recipe);
        return recipe;
    }
}