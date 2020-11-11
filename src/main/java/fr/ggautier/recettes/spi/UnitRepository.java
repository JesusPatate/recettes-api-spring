package fr.ggautier.recettes.spi;

import fr.ggautier.recettes.domain.Unit;
import fr.ggautier.recettes.domain.Units;
import fr.ggautier.recettes.spi.db.UnitDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UnitRepository implements Units {

    private final UnitDAO dao;

    @Autowired
    public UnitRepository(final UnitDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<Unit> getAll() {
        final List<Unit> units = new ArrayList<>();

        this.dao.findAll().forEach(dbModel -> {
            final Unit unit = new Unit(dbModel.getId(), dbModel.getName());
            units.add(unit);
        });

        return units;
    }
}
