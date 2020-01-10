package fr.ggautier.recettes.domain;

import fr.ggautier.arch.annotations.Entity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Recipe {

    private final UUID id;

    private String title;
}
