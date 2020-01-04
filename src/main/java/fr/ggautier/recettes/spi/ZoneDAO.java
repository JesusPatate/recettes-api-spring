package fr.ggautier.recettes.spi;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ZoneDAO extends CrudRepository<RecipeDbModel, UUID> {

}
