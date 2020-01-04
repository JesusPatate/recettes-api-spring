package fr.ggautier.recettes.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class JsonRecipe {

    private UUID id;

    private String title;
}
