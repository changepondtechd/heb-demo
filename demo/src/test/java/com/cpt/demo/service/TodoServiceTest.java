package com.cpt.demo.service;

import com.cpt.demo.dto.TodoRequestDto;
import com.cpt.demo.dto.TodoResponseDto;
import com.cpt.demo.entity.Todo;
import com.cpt.demo.entity.TodoPriority;
import com.cpt.demo.entity.TodoStatus;
import com.cpt.demo.exception.TodoNotFoundException;
import com.cpt.demo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TodoService Tests")
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Todo todo;
    private TodoRequestDto requestDto;

    @BeforeEach
    void setUp() {
        todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Todo");
        todo.setDescription("This is a test todo");
        todo.setStatus(TodoStatus.TODO);
        todo.setPriority(TodoPriority.HIGH);
        todo.setDueDate(LocalDate.now().plusDays(7));
        todo.setCreatedAt(LocalDateTime.now());
        todo.setUpdatedAt(LocalDateTime.now());

        requestDto = new TodoRequestDto();
        requestDto.setTitle("Test Todo");
        requestDto.setDescription("This is a test todo");
        requestDto.setStatus(TodoStatus.TODO);
        requestDto.setPriority(TodoPriority.HIGH);
        requestDto.setDueDate(LocalDate.now().plusDays(7));
    }

    // ==================== AC1: Create Todo ====================

    @Test
    @DisplayName("AC1: Create todo - Happy path")
    void testCreateTodo_Success() {
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        TodoResponseDto response = todoService.createTodo(requestDto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Todo", response.getTitle());
        assertEquals(TodoStatus.TODO, response.getStatus());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    @DisplayName("AC1: Create todo - Sets auto-generated timestamps")
    void testCreateTodo_TimestampsAreSet() {
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        TodoResponseDto response = todoService.createTodo(requestDto);

        assertNotNull(response.getCreatedAt());
        assertNotNull(response.getUpdatedAt());
    }

    // ==================== AC2: Get Todo by ID ====================

    @Test
    @DisplayName("AC2: Get todo by ID - Happy path")
    void testGetTodoById_Success() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        TodoResponseDto response = todoService.getTodoById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Todo", response.getTitle());
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("AC2: Get todo by ID - Throws exception when not found")
    void testGetTodoById_NotFound() {
        when(todoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.getTodoById(999L));
        verify(todoRepository, times(1)).findById(999L);
    }

    // ==================== AC3: Get All Todos ====================

    @Test
    @DisplayName("AC3: Get all todos - Returns non-empty list")
    void testGetAllTodos_Success() {
        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setTitle("Second Todo");
        todo2.setStatus(TodoStatus.IN_PROGRESS);
        todo2.setPriority(TodoPriority.MEDIUM);

        when(todoRepository.findAll()).thenReturn(Arrays.asList(todo, todo2));

        List<TodoResponseDto> todos = todoService.getAllTodos();

        assertNotNull(todos);
        assertEquals(2, todos.size());
        assertEquals("Test Todo", todos.get(0).getTitle());
        assertEquals("Second Todo", todos.get(1).getTitle());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("AC3: Get all todos - Returns empty list")
    void testGetAllTodos_Empty() {
        when(todoRepository.findAll()).thenReturn(Arrays.asList());

        List<TodoResponseDto> todos = todoService.getAllTodos();

        assertNotNull(todos);
        assertTrue(todos.isEmpty());
        verify(todoRepository, times(1)).findAll();
    }

    // ==================== AC4: Update Todo ====================

    @Test
    @DisplayName("AC4: Update todo - Happy path")
    void testUpdateTodo_Success() {
        TodoRequestDto updateRequest = new TodoRequestDto();
        updateRequest.setTitle("Updated Todo");
        updateRequest.setDescription("Updated description");
        updateRequest.setStatus(TodoStatus.IN_PROGRESS);
        updateRequest.setPriority(TodoPriority.MEDIUM);

        Todo updatedTodo = new Todo();
        updatedTodo.setId(1L);
        updatedTodo.setTitle("Updated Todo");
        updatedTodo.setDescription("Updated description");
        updatedTodo.setStatus(TodoStatus.IN_PROGRESS);
        updatedTodo.setPriority(TodoPriority.MEDIUM);
        updatedTodo.setCreatedAt(todo.getCreatedAt());
        updatedTodo.setUpdatedAt(LocalDateTime.now());

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(updatedTodo);

        TodoResponseDto response = todoService.updateTodo(1L, updateRequest);

        assertNotNull(response);
        assertEquals("Updated Todo", response.getTitle());
        assertEquals(TodoStatus.IN_PROGRESS, response.getStatus());
        assertEquals(TodoPriority.MEDIUM, response.getPriority());
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    @DisplayName("AC4: Update todo - Throws exception when not found")
    void testUpdateTodo_NotFound() {
        when(todoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.updateTodo(999L, requestDto));
        verify(todoRepository, times(1)).findById(999L);
    }

    // ==================== AC5: Delete Todo ====================

    @Test
    @DisplayName("AC5: Delete todo - Happy path")
    void testDeleteTodo_Success() {
        when(todoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(todoRepository).deleteById(1L);

        assertDoesNotThrow(() -> todoService.deleteTodo(1L));
        verify(todoRepository, times(1)).existsById(1L);
        verify(todoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("AC5: Delete todo - Throws exception when not found")
    void testDeleteTodo_NotFound() {
        when(todoRepository.existsById(999L)).thenReturn(false);

        assertThrows(TodoNotFoundException.class, () -> todoService.deleteTodo(999L));
        verify(todoRepository, times(1)).existsById(999L);
        verify(todoRepository, never()).deleteById(any());
    }
}
