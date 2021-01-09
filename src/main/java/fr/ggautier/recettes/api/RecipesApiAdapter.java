package fr.ggautier.recettes.api;

import fr.ggautier.arch.annotations.Adapter;
import fr.ggautier.recettes.domain.ICanFindRecipes;
import fr.ggautier.recettes.domain.IManageRecipes;
import fr.ggautier.recettes.domain.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Adapter
@Component
public class RecipesApiAdapter {

    private final IManageRecipes managementService;

    private final ICanFindRecipes browsingService;

    private final RecipeMapper mapper;

    @Autowired
    public RecipesApiAdapter(
        final IManageRecipes managementService,
        final ICanFindRecipes browsingService,
        final RecipeMapper mapper
    ) {
        this.managementService = managementService;
        this.browsingService = browsingService;
        this.mapper = mapper;
    }

    List<Recipe> getAll() {
        return this.browsingService.getAll();
    }

    List<RecipeDto> search(final String term) throws Exception {
        final List<Recipe> recipes = this.browsingService.search(term);
        final List<RecipeDto> dtos = recipes.stream()
            .map(this.mapper::fromRecipe)
            .collect(Collectors.toList());

        return dtos;
    }

    Recipe store(final RecipeDto dto) throws Exception {
        final Recipe recipe = this.mapper.toRecipe(dto);
        this.managementService.store(recipe);
        return recipe;
    }

    void delete(final UUID recipeId) throws NoSuchElementException {
        this.managementService.delete(recipeId);
    }
}
