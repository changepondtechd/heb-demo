package com.cpt.demo.repository;

import com.cpt.demo.entity.Todo;
import com.cpt.demo.entity.TodoPriority;
import com.cpt.demo.entity.TodoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Todo testTodo;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();

        testTodo = new Todo();
        testTodo.setTitle("Test Todo");
        testTodo.setDescription("Test Description");
        testTodo.setStatus(TodoStatus.TODO);
        testTodo.setPriority(TodoPriority.HIGH);
        testTodo.setDueDate(LocalDate.now().plusDays(1));
        testTodo.setCreatedAt(now);
        testTodo.setUpdatedAt(now);
    }

    // ============ AC1: Create a Todo Task ============

    @Test
    void testSave_HappyPath_SavesAndReturnsEntity() {
        Todo savedTodo = todoRepository.save(testTodo);
        entityManager.flush();

        assertNotNull(savedTodo.getId());
        assertEquals("Test Todo", savedTodo.getTitle());
        assertEquals("Test Description", savedTodo.getDescription());
        assertEquals(TodoStatus.TODO, savedTodo.getStatus());
        assertEquals(TodoPriority.HIGH, savedTodo.getPriority());
        assertNotNull(savedTodo.getCreatedAt());
        assertNotNull(savedTodo.getUpdatedAt());
    }

    @Test
    void testSave_WithoutDescription_SavesSuccessfully() {
        testTodo.setDescription(null);
        
        Todo savedTodo = todoRepository.save(testTodo);
        entityManager.flush();

        assertNotNull(savedTodo.getId());
        assertEquals("Test Todo", savedTodo.getTitle());
        assertNull(savedTodo.getDescription());
    }

    // ============ AC2: Get a Todo Task by ID ============

    @Test
    void testFindById_HappyPath_ReturnsOptionalWithTodo() {
        Todo savedTodo = todoRepository.save(testTodo);
        entityManager.flush();
        entityManager.clear();

        Optional<Todo> result = todoRepository.findById(savedTodo.getId());

        assertTrue(result.isPresent());
        assertEquals("Test Todo", result.get().getTitle());
        assertEquals(TodoStatus.TODO, result.get().getStatus());
    }

    @Test
    void testFindById_NotFound_ReturnsEmptyOptional() {
        Optional<Todo> result = todoRepository.findById(999L);

        assertFalse(result.isPresent());
    }

    // ============ AC3: Get All Todo Tasks ============

    @Test
    void testFindAll_HappyPath_ReturnsListOfTodos() {
        Todo todo1 = new Todo();
        todo1.setTitle("Todo 1");
        todo1.setStatus(TodoStatus.TODO);
        todo1.setPriority(TodoPriority.HIGH);
        todo1.setCreatedAt(now);
        todo1.setUpdatedAt(now);

        Todo todo2 = new Todo();
        todo2.setTitle("Todo 2");
        todo2.setStatus(TodoStatus.IN_PROGRESS);
        todo2.setPriority(TodoPriority.MEDIUM);
        todo2.setCreatedAt(now);
        todo2.setUpdatedAt(now);

        todoRepository.save(todo1);
        todoRepository.save(todo2);
        entityManager.flush();

        List<Todo> result = todoRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(t -> t.getTitle().equals("Todo 1")));
        assertTrue(result.stream().anyMatch(t -> t.getTitle().equals("Todo 2")));
    }

    @Test
    void testFindAll_EmptyRepository_ReturnsEmptyList() {
        List<Todo> result = todoRepository.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // ============ AC4: Update a Todo Task ============

    @Test
    void testSave_UpdateExisting_UpdatesEntity() {
        Todo savedTodo = todoRepository.save(testTodo);
        entityManager.flush();

        savedTodo.setTitle("Updated Title");
        savedTodo.setStatus(TodoStatus.IN_PROGRESS);
        savedTodo.setPriority(TodoPriority.LOW);

        Todo updatedTodo = todoRepository.save(savedTodo);
        entityManager.flush();
        entityManager.clear();

        Optional<Todo> result = todoRepository.findById(updatedTodo.getId());

        assertTrue(result.isPresent());
        assertEquals("Updated Title", result.get().getTitle());
        assertEquals(TodoStatus.IN_PROGRESS, result.get().getStatus());
        assertEquals(TodoPriority.LOW, result.get().getPriority());
    }

    @Test
    void testSave_UpdatePreservesCreatedAt() {
        Todo savedTodo = todoRepository.save(testTodo);
        entityManager.flush();
        LocalDateTime originalCreatedAt = savedTodo.getCreatedAt();

        savedTodo.setTitle("Updated");
        todoRepository.save(savedTodo);
        entityManager.flush();
        entityManager.clear();

        Optional<Todo> result = todoRepository.findById(savedTodo.getId());

        assertTrue(result.isPresent());
        assertEquals(originalCreatedAt, result.get().getCreatedAt());
    }

    // ============ AC5: Delete a Todo Task ============

    @Test
    void testDeleteById_HappyPath_DeletesEntity() {
        Todo savedTodo = todoRepository.save(testTodo);
        entityManager.flush();

        todoRepository.deleteById(savedTodo.getId());
        entityManager.flush();

        Optional<Todo> result = todoRepository.findById(savedTodo.getId());

        assertFalse(result.isPresent());
    }

    @Test
    void testExistsById_HappyPath_ReturnsTrue() {
        Todo savedTodo = todoRepository.save(testTodo);
        entityManager.flush();

        boolean exists = todoRepository.existsById(savedTodo.getId());

        assertTrue(exists);
    }

    @Test
    void testExistsById_NotFound_ReturnsFalse() {
        boolean exists = todoRepository.existsById(999L);

        assertFalse(exists);
    }
}
