package chnu._3.myproject.service;/*
  @author Bogdan
  @project myproject
  @class DuckService
  @version 1.0.0
  @since 19.04.2025 - 20.03
*/


import chnu._3.myproject.model.Duck;
import chnu._3.myproject.repository.DuckRepository;
import chnu._3.myproject.request.DuckCreateRequest;
import chnu._3.myproject.request.DuckUpdateRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @PostConstruct
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

    public Duck create(DuckCreateRequest request){
        Duck duck = mapToDuck(request);
        duck.setCreateDate(LocalDateTime.now());
        duck.setUpdateDate(new ArrayList<LocalDateTime>());
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

    private Duck mapToDuck(DuckCreateRequest request){
        return new Duck(request.name(), request.code(), request.description());
    }

    public Duck update(DuckUpdateRequest request){
        Duck duckPersisted = duckRepository.findById(request.id()).orElse(null);
        if(duckPersisted != null) {
            List<LocalDateTime> updateDates = duckPersisted.getUpdateDate();
            updateDates.add(LocalDateTime.now());
            Duck duckToUpdate =
                    Duck.builder()
                            .id(request.id())
                            .name(request.name())
                            .code(request.code())
                            .description(request.description())
                            .createDate(duckPersisted.getCreateDate())
                            .updateDate(updateDates)
                            .build();
            return duckRepository.save(duckToUpdate);

        }
        return null;
    }

    public void delById(String id) {
        duckRepository.deleteById(id);
    }
}