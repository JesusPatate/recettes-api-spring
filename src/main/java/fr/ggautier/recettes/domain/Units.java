package fr.ggautier.recettes.domain;

import fr.ggautier.arch.annotations.Repository;

import java.util.List;

@Repository
public interface Units {

    List<Unit> getAll();
}
