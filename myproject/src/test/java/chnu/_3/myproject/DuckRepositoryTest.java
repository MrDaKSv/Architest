package chnu._3.myproject;/*
  @author Bogdan
  @project Architest
  @class RepositoryTest
  @version 1.0.0
  @since 24.04.2025 - 19.55
*/



import chnu._3.myproject.model.Duck;
import chnu._3.myproject.repository.DuckRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest

public class DuckRepositoryTest {

    @Autowired
    DuckRepository duckRepository;

    @BeforeEach
    void initData() {
        duckRepository.saveAll(List.of(
                new Duck("Donald", "D001", "Funny cartoon duck ###test"),
                new Duck("Daisy", "D002", "Loves flowers ###test"),
                new Duck("Scrooge", "D003", "Very rich duck ###test")
        ));
    }

    @AfterEach
    void cleanUp() {
        duckRepository.deleteAll(
                duckRepository.findAll().stream()
                        .filter(d -> d.getDescription().contains("###test"))
                        .toList()
        );
    }

    @Test
    void shouldContainThreeTestDucks() {
        List<Duck> ducks = duckRepository.findAll().stream()
                .filter(d -> d.getDescription().contains("###test"))
                .toList();
        assertEquals(3, ducks.size());
    }

    @Test
    void shouldSaveDuckWithGeneratedId() {
        Duck duck = new Duck("Huey", "D004", "One of the triplets ###test");
        duckRepository.save(duck);

        Duck saved = duckRepository.findAll().stream()
                .filter(d -> d.getName().equals("Huey"))
                .findFirst()
                .orElse(null);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertFalse(saved.getId().isEmpty());
    }

    @Test
    void shouldDeleteDuckById() {
        Duck duck = new Duck("Louie", "D005", "Green triplet ###test");
        Duck saved = duckRepository.save(duck);

        duckRepository.deleteById(saved.getId());

        assertFalse(duckRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void shouldUpdateDuckDescription() {
        Duck duck = duckRepository.save(new Duck("Webby", "D006", "Smart duck ###test"));
        duck.setDescription("Brave duck ###test");
        duckRepository.save(duck);

        Duck updated = duckRepository.findById(duck.getId()).orElse(null);
        assertEquals("Brave duck ###test", updated.getDescription());
    }

    @Test
    void shouldReturnEmptyListForNonMatchingCode() {
        List<Duck> result = duckRepository.findAll().stream()
                .filter(d -> d.getCode().equals("UNKNOWN"))
                .toList();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindDuckByCode() {
        List<Duck> result = duckRepository.findAll().stream()
                .filter(d -> d.getCode().equals("D003"))
                .toList();

        assertEquals(1, result.size());
        assertEquals("Scrooge", result.get(0).getName());
    }

    @Test
    void shouldHaveUniqueIdsForNewDucks() {
        Duck d1 = new Duck("Quack", "D007", "Test duck 1 ###test");
        Duck d2 = new Duck("Crack", "D008", "Test duck 2 ###test");

        duckRepository.saveAll(List.of(d1, d2));

        List<Duck> saved = duckRepository.findAll().stream()
                .filter(d -> d.getDescription().contains("###test"))
                .toList();

        assertNotEquals(saved.get(0).getId(), saved.get(1).getId());
    }

    @Test
    void shouldCountDucksWithTestMarkerInDescription() {
        long initialCount = duckRepository.findAll().stream()
                .filter(d -> d.getDescription().contains("###test"))
                .count();

        duckRepository.save(new Duck("Track", "D009", "New duck ###test"));

        long newCount = duckRepository.findAll().stream()
                .filter(d -> d.getDescription().contains("###test"))
                .count();

        assertEquals(initialCount + 1, newCount);
    }

    @Test
    void shouldFindDuckAndVerifyAllFields() {
        Duck duck = new Duck("Launchpad", "D010", "Pilot duck ###test");
        Duck saved = duckRepository.save(duck);

        Duck fromDb = duckRepository.findById(saved.getId()).orElse(null);

        assertNotNull(fromDb);
        assertEquals("Launchpad", fromDb.getName());
        assertEquals("D010", fromDb.getCode());
        assertEquals("Pilot duck ###test", fromDb.getDescription());
    }
}
