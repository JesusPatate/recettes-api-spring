package fr.ggautier.recettes.api;

import fr.ggautier.arch.annotations.Adapter;
import fr.ggautier.arch.annotations.rest.Resource;
import fr.ggautier.recettes.domain.Unit;
import fr.ggautier.recettes.domain.UnitManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Resource
@Adapter
@RestController
@RequestMapping(UnitController.ROUTE)
public class UnitController {

    static final String ROUTE = "/units";

    private final UnitManagementService service;

    @Autowired
    public UnitController(final UnitManagementService service) {
        this.service = service;
    }

    @GetMapping
    public List<Unit> getAll() {
        return this.service.getAll();
    }
}
