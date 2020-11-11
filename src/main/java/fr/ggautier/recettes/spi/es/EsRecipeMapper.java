package fr.ggautier.recettes.spi.es;

import fr.ggautier.recettes.domain.Recipe;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EsRecipeMapper {

    public Recipe toRecipe(final EsRecipe esRecipe) {
        return new Recipe(UUID.fromString(esRecipe.getId()), esRecipe.getTitle());
    }
}
