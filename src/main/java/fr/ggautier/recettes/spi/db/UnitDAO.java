package fr.ggautier.recettes.spi.db;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UnitDAO extends CrudRepository<DbUnit, UUID> {

}
