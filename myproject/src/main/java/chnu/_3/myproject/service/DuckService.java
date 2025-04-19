package chnu._3.myproject.service;/*
  @author Bogdan
  @project myproject
  @class DuckService
  @version 1.0.0
  @since 19.04.2025 - 20.03
*/


import chnu._3.myproject.model.Duck;
import chnu._3.myproject.repository.DuckRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DuckService {
    private final DuckRepository duckRepository;

    private final List<Duck> ducks = new ArrayList<>();

    {
        ducks.add(new Duck("duck1", "000001", "duck description1"));
        ducks.add(new Duck("duck2", "000002", "duck description2"));
        ducks.add(new Duck("duck3", "000003", "duck description3"));
    }

    //@PostConstruct
    void init() {
        duckRepository.deleteAll();
        duckRepository.saveAll(ducks);
    }

    //CRUD

    public List<Duck> getAll() {
        return duckRepository.findAll();
    }

    public Duck getById(String id) {
        return duckRepository.findById(id).orElse(null);
    }

    public Duck create(Duck duck) {
        return duckRepository.save(duck);
    }

    public Duck update(String id, Duck updatedDuck) {
        // Перевіряємо, чи існує качка з таким id
        Duck existingDuck = duckRepository.findById(id).orElse(null);

        if (existingDuck != null) {
            // Оновлюємо властивості існуючої качки
            existingDuck.setName(updatedDuck.getName());
            existingDuck.setCode(updatedDuck.getCode());
            existingDuck.setDescription(updatedDuck.getDescription());

            // Зберігаємо оновлену качку
            return duckRepository.save(existingDuck);
        }
        return null;
    }

    public void delById(String id) {
        duckRepository.deleteById(id);
    }
}

