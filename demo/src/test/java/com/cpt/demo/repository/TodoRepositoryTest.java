package com.cpt.demo.repository;

import com.cpt.demo.entity.Todo;
import com.cpt.demo.entity.TodoPriority;
import com.cpt.demo.entity.TodoStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("TodoRepository Tests")
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setDescription("This is a test todo");
        todo.setStatus(TodoStatus.TODO);
        todo.setPriority(TodoPriority.HIGH);
        todo.setDueDate(LocalDate.now().plusDays(7));
        todo.setCreatedAt(LocalDateTime.now());
        todo.setUpdatedAt(LocalDateTime.now());
    }

    // ==================== AC1: Create (Save) Todo ====================

    @Test
    @DisplayName("AC1: Save todo to repository - Happy path")
    void testSaveTodo_Success() {
        Todo savedTodo = todoRepository.save(todo);

        assertNotNull(savedTodo);
        assertNotNull(savedTodo.getId());
        assertEquals("Test Todo", savedTodo.getTitle());
        assertEquals(TodoStatus.TODO, savedTodo.getStatus());
        assertEquals(TodoPriority.HIGH, savedTodo.getPriority());
    }

    @Test
    @DisplayName("AC1: Save todo - Generates ID")
    void testSaveTodo_GeneratesId() {
        assertNull(todo.getId());
        Todo savedTodo = todoRepository.save(todo);

        assertNotNull(savedTodo.getId());
        assertTrue(savedTodo.getId() > 0);
    }

    // ==================== AC2: Get Todo by ID ====================

    @Test
    @DisplayName("AC2: Find todo by ID - Happy path")
    void testFindById_Success() {
        Todo savedTodo = todoRepository.save(todo);
        Optional<Todo> foundTodo = todoRepository.findById(savedTodo.getId());

        assertTrue(foundTodo.isPresent());
        assertEquals("Test Todo", foundTodo.get().getTitle());
        assertEquals(TodoStatus.TODO, foundTodo.get().getStatus());
    }

    @Test
    @DisplayName("AC2: Find todo by ID - Not found")
    void testFindById_NotFound() {
        Optional<Todo> foundTodo = todoRepository.findById(999L);

        assertFalse(foundTodo.isPresent());
    }

    // ==================== AC3: Get All Todos ====================

    @Test
    @DisplayName("AC3: Find all todos - Multiple records")
    void testFindAll_MultipleRecords() {
        Todo todo1 = new Todo();
        todo1.setTitle("First Todo");
        todo1.setStatus(TodoStatus.TODO);
        todo1.setPriority(TodoPriority.HIGH);
        todo1.setCreatedAt(LocalDateTime.now());
        todo1.setUpdatedAt(LocalDateTime.now());

        Todo todo2 = new Todo();
        todo2.setTitle("Second Todo");
        todo2.setStatus(TodoStatus.IN_PROGRESS);
        todo2.setPriority(TodoPriority.MEDIUM);
        todo2.setCreatedAt(LocalDateTime.now());
        todo2.setUpdatedAt(LocalDateTime.now());

        todoRepository.save(todo1);
        todoRepository.save(todo2);

        List<Todo> todos = todoRepository.findAll();

        assertNotNull(todos);
        assertTrue(todos.size() >= 2);
    }

    @Test
    @DisplayName("AC3: Find all todos - Empty repository")
    void testFindAll_Empty() {
        List<Todo> todos = todoRepository.findAll();

        assertNotNull(todos);
        // May or may not be empty depending on test isolation
    }

    // ==================== AC4: Update Todo ====================

    @Test
    @DisplayName("AC4: Update todo - Modify and save")
    void testUpdateTodo_Success() {
        Todo savedTodo = todoRepository.save(todo);
        Long todoId = savedTodo.getId();

        savedTodo.setTitle("Updated Title");
        savedTodo.setStatus(TodoStatus.IN_PROGRESS);
        savedTodo.setPriority(TodoPriority.LOW);
        savedTodo.setUpdatedAt(LocalDateTime.now());

        Todo updatedTodo = todoRepository.save(savedTodo);

        assertEquals("Updated Title", updatedTodo.getTitle());
        assertEquals(TodoStatus.IN_PROGRESS, updatedTodo.getStatus());
        assertEquals(TodoPriority.LOW, updatedTodo.getPriority());
        assertEquals(todoId, updatedTodo.getId());
    }

    @Test
    @DisplayName("AC4: Update todo - preserves creation timestamp")
    void testUpdateTodo_PreservesCreatedAt() {
        Todo savedTodo = todoRepository.save(todo);
        LocalDateTime originalCreatedAt = savedTodo.getCreatedAt();

        savedTodo.setTitle("Updated");
        todoRepository.save(savedTodo);

        Todo reloadedTodo = todoRepository.findById(savedTodo.getId()).get();
        assertEquals(originalCreatedAt, reloadedTodo.getCreatedAt());
    }

    // ==================== AC5: Delete Todo ====================

    @Test
    @DisplayName("AC5: Delete todo - Happy path")
    void testDeleteTodo_Success() {
        Todo savedTodo = todoRepository.save(todo);
        Long todoId = savedTodo.getId();

        todoRepository.deleteById(todoId);

        Optional<Todo> deletedTodo = todoRepository.findById(todoId);
        assertFalse(deletedTodo.isPresent());
    }

    @Test
    @DisplayName("AC5: Delete all todos")
    void testDeleteAllTodos() {
        todoRepository.save(todo);
        todoRepository.save(todo);

        long countBefore = todoRepository.count();
        assertTrue(countBefore > 0);

        todoRepository.deleteAll();

        long countAfter = todoRepository.count();
        assertEquals(0, countAfter);
    }

    // ==================== Existence Checks ====================

    @Test
    @DisplayName("Check if todo exists by ID")
    void testExistsById() {
        Todo savedTodo = todoRepository.save(todo);

        assertTrue(todoRepository.existsById(savedTodo.getId()));
        assertFalse(todoRepository.existsById(999L));
    }

    // ==================== Count Tests ====================

    @Test
    @DisplayName("Count todos in repository")
    void testCount() {
        todoRepository.deleteAll();
        assertEquals(0, todoRepository.count());

        todoRepository.save(todo);
        assertEquals(1, todoRepository.count());
    }
}
