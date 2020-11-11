package fr.ggautier.recettes.domain;

import fr.ggautier.arch.annotations.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recipe {

    @EqualsAndHashCode.Include
    private final UUID id;

    private String title;
}
