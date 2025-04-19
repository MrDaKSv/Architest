package chnu._3.myproject.controller;/*
  @author Bogdan
  @project myproject
  @class DuckRestController
  @version 1.0.0
  @since 19.04.2025 - 22.04
*/

import chnu._3.myproject.model.Duck;
import chnu._3.myproject.service.DuckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/ducks/")
@RequiredArgsConstructor

public class DuckRestController {
    private final DuckService duckService;

    @GetMapping
    public List<Duck> showAll(){ return duckService.getAll(); }

    @GetMapping("{id}")
    public Duck showOneById(@PathVariable String id) { return duckService.getById(id); }

    @PostMapping
    public Duck insert(@RequestBody Duck duck) { return duckService.create(duck); }

    @PutMapping("/{id}")
    public Duck edit(@PathVariable String id, @RequestBody Duck updatedDuck) {
        return duckService.update(id, updatedDuck);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) { duckService.delById(id); }
}
