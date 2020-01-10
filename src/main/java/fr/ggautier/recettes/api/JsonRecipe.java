package fr.ggautier.recettes.api;

import fr.ggautier.arch.annotations.rest.Representation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Representation
@Getter
@AllArgsConstructor
public class JsonRecipe {

    private UUID id;

    @NotBlank
    private String title;

    @Valid
    @NotEmpty
    private List<JsonIngredient> ingredients;
}
