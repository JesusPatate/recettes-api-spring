package fr.ggautier.recettes.domain;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecipeFinder {

    public List<Recipe> search(final String term) {
        final Recipe recipe1 = new Recipe(UUID.fromString("cc83467c-8192-454f-90bb-7e88bfdd8214"), "recipe1");
        final Recipe recipe2 = new Recipe(UUID.fromString("436c61f5-0e81-4194-8e09-13f2fa043c7e"), "recipe2");

        final List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        return recipes;
    }
}
