package fr.ggautier.recettes.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitManagementService {

    private final Units units;

    @Autowired
    public UnitManagementService(final Units units) {
        this.units = units;
    }

    public List<Unit> getAll() {
        return this.units.getAll();
    }
}
