package com.cpt.demo.service;

import com.cpt.demo.dto.TodoRequestDto;
import com.cpt.demo.dto.TodoResponseDto;
import com.cpt.demo.entity.Todo;
import com.cpt.demo.entity.TodoPriority;
import com.cpt.demo.entity.TodoStatus;
import com.cpt.demo.exception.TodoNotFoundException;
import com.cpt.demo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTests {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Todo testTodo;
    private TodoRequestDto requestDto;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();

        testTodo = new Todo();
        testTodo.setId(1L);
        testTodo.setTitle("Test Todo");
        testTodo.setDescription("Test Description");
        testTodo.setStatus(TodoStatus.TODO);
        testTodo.setPriority(TodoPriority.HIGH);
        testTodo.setDueDate(LocalDate.now().plusDays(1));
        testTodo.setCreatedAt(now);
        testTodo.setUpdatedAt(now);

        requestDto = new TodoRequestDto();
        requestDto.setTitle("Test Todo");
        requestDto.setDescription("Test Description");
        requestDto.setStatus("TODO");
        requestDto.setPriority("HIGH");
        requestDto.setDueDate(LocalDate.now().plusDays(1));
    }

    // ============ AC1: Create a Todo Task ============

    @Test
    void testCreateTodo_HappyPath_ReturnsTodoResponseDto() {
        when(todoRepository.save(any(Todo.class))).thenReturn(testTodo);

        TodoResponseDto result = todoService.createTodo(requestDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Todo", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertEquals(TodoStatus.TODO, result.getStatus());
        assertEquals(TodoPriority.HIGH, result.getPriority());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());

        verify(todoRepository).save(any(Todo.class));
    }

    @Test
    void testCreateTodo_WithStatusEnum_ParsesCorrectly() {
        requestDto.setStatus("in_progress");
        requestDto.setPriority("medium");

        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> {
            Todo arg = invocation.getArgument(0);
            arg.setId(1L);
            arg.setCreatedAt(now);
            arg.setUpdatedAt(now);
            return arg;
        });

        TodoResponseDto result = todoService.createTodo(requestDto);

        assertNotNull(result);
        assertEquals(TodoStatus.IN_PROGRESS, result.getStatus());
        assertEquals(TodoPriority.MEDIUM, result.getPriority());
    }

    // ============ AC2: Get a Todo Task by ID ============

    @Test
    void testGetTodoById_HappyPath_ReturnsTodoResponseDto() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(testTodo));

        TodoResponseDto result = todoService.getTodoById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Todo", result.getTitle());
        assertEquals(TodoStatus.TODO, result.getStatus());

        verify(todoRepository).findById(1L);
    }

    @Test
    void testGetTodoById_NotFound_ThrowsTodoNotFoundException() {
        when(todoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.getTodoById(999L));

        verify(todoRepository).findById(999L);
    }

    @Test
    void testGetTodoById_NotFound_MessageIncludesId() {
        when(todoRepository.findById(999L)).thenReturn(Optional.empty());

        TodoNotFoundException exception = assertThrows(TodoNotFoundException.class,
                () -> todoService.getTodoById(999L));

        assertTrue(exception.getMessage().contains("999"));
    }

    // ============ AC3: Get All Todo Tasks ============

    @Test
    void testGetAllTodos_HappyPath_ReturnsListOfTodos() {
        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setTitle("Another Todo");
        todo2.setStatus(TodoStatus.IN_PROGRESS);
        todo2.setPriority(TodoPriority.MEDIUM);
        todo2.setCreatedAt(now);
        todo2.setUpdatedAt(now);

        List<Todo> todos = Arrays.asList(testTodo, todo2);
        when(todoRepository.findAll()).thenReturn(todos);

        List<TodoResponseDto> result = todoService.getAllTodos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Todo", result.get(0).getTitle());
        assertEquals("Another Todo", result.get(1).getTitle());

        verify(todoRepository).findAll();
    }

    @Test
    void testGetAllTodos_EmptyList_ReturnsEmptyList() {
        when(todoRepository.findAll()).thenReturn(Arrays.asList());

        List<TodoResponseDto> result = todoService.getAllTodos();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(todoRepository).findAll();
    }

    // ============ AC4: Update a Todo Task ============

    @Test
    void testUpdateTodo_HappyPath_ReturnsUpdatedTodoResponseDto() {
        TodoRequestDto updateDto = new TodoRequestDto();
        updateDto.setTitle("Updated Todo");
        updateDto.setDescription("Updated Description");
        updateDto.setStatus("IN_PROGRESS");
        updateDto.setPriority("MEDIUM");
        updateDto.setDueDate(LocalDate.now().plusDays(5));

        Todo updatedTodo = new Todo();
        updatedTodo.setId(1L);
        updatedTodo.setTitle("Updated Todo");
        updatedTodo.setDescription("Updated Description");
        updatedTodo.setStatus(TodoStatus.IN_PROGRESS);
        updatedTodo.setPriority(TodoPriority.MEDIUM);
        updatedTodo.setDueDate(LocalDate.now().plusDays(5));
        updatedTodo.setCreatedAt(now);
        updatedTodo.setUpdatedAt(LocalDateTime.now());

        when(todoRepository.findById(1L)).thenReturn(Optional.of(testTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(updatedTodo);

        TodoResponseDto result = todoService.updateTodo(1L, updateDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated Todo", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(TodoStatus.IN_PROGRESS, result.getStatus());
        assertEquals(TodoPriority.MEDIUM, result.getPriority());

        verify(todoRepository).findById(1L);
        verify(todoRepository).save(any(Todo.class));
    }

    @Test
    void testUpdateTodo_NotFound_ThrowsTodoNotFoundException() {
        when(todoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class,
                () -> todoService.updateTodo(999L, requestDto));

        verify(todoRepository).findById(999L);
    }

    // ============ AC5: Delete a Todo Task ============

    @Test
    void testDeleteTodo_HappyPath_DeletesSuccessfully() {
        when(todoRepository.existsById(1L)).thenReturn(true);

        todoService.deleteTodo(1L);

        verify(todoRepository).existsById(1L);
        verify(todoRepository).deleteById(1L);
    }

    @Test
    void testDeleteTodo_NotFound_ThrowsTodoNotFoundException() {
        when(todoRepository.existsById(999L)).thenReturn(false);

        assertThrows(TodoNotFoundException.class, () -> todoService.deleteTodo(999L));

        verify(todoRepository).existsById(999L);
        verify(todoRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteTodo_NotFound_MessageIncludesId() {
        when(todoRepository.existsById(999L)).thenReturn(false);

        TodoNotFoundException exception = assertThrows(TodoNotFoundException.class,
                () -> todoService.deleteTodo(999L));

        assertTrue(exception.getMessage().contains("999"));
    }
}
