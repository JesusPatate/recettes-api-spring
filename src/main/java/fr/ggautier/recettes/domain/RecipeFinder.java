package fr.ggautier.recettes.domain;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RecipeFinder {

    public List<Recipe> search(final String term) {
        return Collections.emptyList();
    }
}
