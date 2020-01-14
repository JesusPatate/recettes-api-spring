package fr.ggautier.recettes.spi;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UnitDAO extends CrudRepository<UnitDbModel, UUID> {

}
