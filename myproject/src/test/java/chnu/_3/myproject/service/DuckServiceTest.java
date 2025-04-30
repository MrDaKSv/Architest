package chnu._3.myproject.service;

import chnu._3.myproject.model.Duck;
import chnu._3.myproject.repository.DuckRepository;
import chnu._3.myproject.request.DuckCreateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/*
  @author Bogdan
  @project Architect
  @class DuckServiceTest
  @version 1.0.0
  @since 30.04.2025 - 22.53
*/

@SpringBootTest

class DuckServiceTest {

    @Autowired
    private DuckRepository repository;

    @Autowired
    private DuckService underTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void whenInsertNewItem_ThenCreateDateIsPresent() {
        //given
        DuckCreateRequest request = new DuckCreateRequest("Doodle", "Jump", "Leeroy Jenkins");
        LocalDateTime now = LocalDateTime.now();
        // when
        Duck createdItem = underTest.create(request);
        // then
        assertNotNull(createdItem);
        assertNotNull(createdItem.getId());
        assertEquals("Doodle", createdItem.getName());
        assertEquals("Jump", createdItem.getCode());
        assertEquals("Leeroy Jenkins", createdItem.getDescription());
        assertNotNull(createdItem.getCreateDate());
        assertSame(LocalDateTime.class, createdItem.getCreateDate().getClass());
        assertTrue(createdItem.getCreateDate().isAfter(now));
        assertNotNull(createdItem.getUpdateDate());
        assertSame(ArrayList.class, createdItem.getUpdateDate().getClass());
        assertTrue(createdItem.getUpdateDate().isEmpty());

    }

    @Test
    void whenCreateDuck_ThenNameShouldNotBeNull() {
        DuckCreateRequest request = new DuckCreateRequest("Quacky", "Q123", "A lovely duck");
        Duck createdDuck = underTest.create(request);

        assertNotNull(createdDuck.getName());
        assertEquals("Quacky", createdDuck.getName());
    }

    @Test
    void whenCreateDuck_ThenCodeShouldMatch() {
        DuckCreateRequest request = new DuckCreateRequest("Fluffy", "FLY456", "Flying duck");
        Duck createdDuck = underTest.create(request);

        assertEquals("FLY456", createdDuck.getCode());
    }

    @Test
    void whenCreateDuck_ThenDescriptionShouldMatch() {
        DuckCreateRequest request = new DuckCreateRequest("Storm", "ST123", "Thunder duck");
        Duck createdDuck = underTest.create(request);

        assertEquals("Thunder duck", createdDuck.getDescription());
    }

    @Test
    void whenCreateDuck_ThenItShouldBeSavedInRepository() {
        DuckCreateRequest request = new DuckCreateRequest("Shadow", "SH123", "Stealthy duck");
        underTest.create(request);

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void whenCreateMultipleDucks_ThenAllShouldBeSaved() {
        DuckCreateRequest req1 = new DuckCreateRequest("One", "C1", "Duck one");
        DuckCreateRequest req2 = new DuckCreateRequest("Two", "C2", "Duck two");

        underTest.create(req1);
        underTest.create(req2);

        assertEquals(2, repository.findAll().size());
    }

    @Test
    void whenCreateDuck_ThenUpdateDateShouldBeEmptyInitially() {
        DuckCreateRequest request = new DuckCreateRequest("Snowy", "SN001", "Winter duck");
        Duck createdDuck = underTest.create(request);

        assertNotNull(createdDuck.getUpdateDate());
        assertTrue(createdDuck.getUpdateDate().isEmpty());
    }

    @Test
    void whenCreateDuck_ThenIdShouldBeGenerated() {
        DuckCreateRequest request = new DuckCreateRequest("Bolt", "BL999", "Fast duck");
        Duck createdDuck = underTest.create(request);

        assertNotNull(createdDuck.getId());
        assertFalse(createdDuck.getId().isBlank());

        assertDoesNotThrow(() -> UUID.fromString(createdDuck.getId()));
    }

    @Test
    void whenDuckCreated_ThenCreateDateShouldBeRecent() {
        LocalDateTime before = LocalDateTime.now();
        DuckCreateRequest request = new DuckCreateRequest("Rapid", "RPD", "Speedy duck");
        Duck createdDuck = underTest.create(request);
        LocalDateTime after = LocalDateTime.now();

        assertTrue(createdDuck.getCreateDate().isAfter(before) || createdDuck.getCreateDate().isEqual(before));
        assertTrue(createdDuck.getCreateDate().isBefore(after) || createdDuck.getCreateDate().isEqual(after));
    }

    @Test
    void whenCreateDuck_ThenFindByIdShouldReturnIt() {
        DuckCreateRequest request = new DuckCreateRequest("Hidden", "HDN001", "Secret duck");
        Duck createdDuck = underTest.create(request);

        Duck found = repository.findById(createdDuck.getId()).orElse(null);

        assertNotNull(found);
        assertEquals("Hidden", found.getName());
    }

    @Test
    void whenDeleteAllDucks_ThenRepositoryShouldBeEmpty() {
        DuckCreateRequest request1 = new DuckCreateRequest("Test1", "T1", "First");
        DuckCreateRequest request2 = new DuckCreateRequest("Test2", "T2", "Second");

        underTest.create(request1);
        underTest.create(request2);

        repository.deleteAll();

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void update() {
    }
}