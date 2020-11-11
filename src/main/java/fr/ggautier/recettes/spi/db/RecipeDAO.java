package fr.ggautier.recettes.spi.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeDAO extends CrudRepository<DbRecipe, UUID> {

}
