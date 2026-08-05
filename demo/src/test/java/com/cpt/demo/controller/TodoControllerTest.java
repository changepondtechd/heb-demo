package com.cpt.demo.controller;

import com.cpt.demo.dto.TodoRequestDto;
import com.cpt.demo.dto.TodoResponseDto;
import com.cpt.demo.entity.TodoPriority;
import com.cpt.demo.entity.TodoStatus;
import com.cpt.demo.exception.TodoNotFoundException;
import com.cpt.demo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
@DisplayName("TodoController Tests")
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    private TodoRequestDto requestDto;
    private TodoResponseDto responseDto;

    @BeforeEach
    void setUp() {
        requestDto = new TodoRequestDto();
        requestDto.setTitle("Test Todo");
        requestDto.setDescription("This is a test todo");
        requestDto.setStatus(TodoStatus.TODO);
        requestDto.setPriority(TodoPriority.HIGH);
        requestDto.setDueDate(LocalDate.now().plusDays(7));

        responseDto = new TodoResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Test Todo");
        responseDto.setDescription("This is a test todo");
        responseDto.setStatus(TodoStatus.TODO);
        responseDto.setPriority(TodoPriority.HIGH);
        responseDto.setDueDate(LocalDate.now().plusDays(7));
        responseDto.setCreatedAt(LocalDateTime.now());
        responseDto.setUpdatedAt(LocalDateTime.now());
    }

    // ==================== AC1: Create a Todo Task ====================

    @Test
    @DisplayName("AC1: Create todo - Happy path (201 Created)")
    void testCreateTodo_Success() throws Exception {
        when(todoService.createTodo(any(TodoRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Todo")))
                .andExpect(jsonPath("$.description", is("This is a test todo")))
                .andExpect(jsonPath("$.status", is("TODO")))
                .andExpect(jsonPath("$.priority", is("HIGH")));

        verify(todoService, times(1)).createTodo(any(TodoRequestDto.class));
    }

    @Test
    @DisplayName("AC1: Create todo - Validation error (400 Bad Request)")
    void testCreateTodo_MissingTitle() throws Exception {
        TodoRequestDto invalidRequest = new TodoRequestDto();
        invalidRequest.setDescription("Missing title");
        invalidRequest.setStatus(TodoStatus.TODO);
        invalidRequest.setPriority(TodoPriority.MEDIUM);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    // ==================== AC2: Get a Todo Task by ID ====================

    @Test
    @DisplayName("AC2: Get todo by ID - Happy path (200 OK)")
    void testGetTodoById_Success() throws Exception {
        when(todoService.getTodoById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test Todo")))
                .andExpect(jsonPath("$.description", is("This is a test todo")));

        verify(todoService, times(1)).getTodoById(1L);
    }

    @Test
    @DisplayName("AC2: Get todo by ID - Not found (404 Not Found)")
    void testGetTodoById_NotFound() throws Exception {
        when(todoService.getTodoById(999L))
                .thenThrow(new TodoNotFoundException("Todo with id 999 not found"));

        mockMvc.perform(get("/api/todos/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", containsString("not found")));

        verify(todoService, times(1)).getTodoById(999L);
    }

    // ==================== AC3: Get All Todo Tasks ====================

    @Test
    @DisplayName("AC3: Get all todos - Happy path (200 OK with list)")
    void testGetAllTodos_Success() throws Exception {
        List<TodoResponseDto> todos = Arrays.asList(responseDto, responseDto);
        when(todoService.getAllTodos()).thenReturn(todos);

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test Todo")));

        verify(todoService, times(1)).getAllTodos();
    }

    @Test
    @DisplayName("AC3: Get all todos - Empty list (200 OK with empty array)")
    void testGetAllTodos_Empty() throws Exception {
        when(todoService.getAllTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(todoService, times(1)).getAllTodos();
    }

    // ==================== AC4: Update a Todo Task ====================

    @Test
    @DisplayName("AC4: Update todo - Happy path (200 OK)")
    void testUpdateTodo_Success() throws Exception {
        TodoResponseDto updatedResponse = new TodoResponseDto();
        updatedResponse.setId(1L);
        updatedResponse.setTitle("Updated Todo");
        updatedResponse.setDescription("Updated description");
        updatedResponse.setStatus(TodoStatus.IN_PROGRESS);
        updatedResponse.setPriority(TodoPriority.MEDIUM);
        updatedResponse.setCreatedAt(LocalDateTime.now());
        updatedResponse.setUpdatedAt(LocalDateTime.now());

        TodoRequestDto updateRequest = new TodoRequestDto();
        updateRequest.setTitle("Updated Todo");
        updateRequest.setDescription("Updated description");
        updateRequest.setStatus(TodoStatus.IN_PROGRESS);
        updateRequest.setPriority(TodoPriority.MEDIUM);

        when(todoService.updateTodo(eq(1L), any(TodoRequestDto.class))).thenReturn(updatedResponse);

        mockMvc.perform(put("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Updated Todo")))
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")))
                .andExpect(jsonPath("$.priority", is("MEDIUM")));

        verify(todoService, times(1)).updateTodo(eq(1L), any(TodoRequestDto.class));
    }

    @Test
    @DisplayName("AC4: Update todo - Not found (404 Not Found)")
    void testUpdateTodo_NotFound() throws Exception {
        when(todoService.updateTodo(eq(999L), any(TodoRequestDto.class)))
                .thenThrow(new TodoNotFoundException("Todo with id 999 not found"));

        mockMvc.perform(put("/api/todos/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", containsString("not found")));

        verify(todoService, times(1)).updateTodo(eq(999L), any(TodoRequestDto.class));
    }

    // ==================== AC5: Delete a Todo Task ====================

    @Test
    @DisplayName("AC5: Delete todo - Happy path (204 No Content)")
    void testDeleteTodo_Success() throws Exception {
        doNothing().when(todoService).deleteTodo(1L);

        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());

        verify(todoService, times(1)).deleteTodo(1L);
    }

    @Test
    @DisplayName("AC5: Delete todo - Not found (404 Not Found)")
    void testDeleteTodo_NotFound() throws Exception {
        doThrow(new TodoNotFoundException("Todo with id 999 not found"))
                .when(todoService).deleteTodo(999L);

        mockMvc.perform(delete("/api/todos/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", containsString("not found")));

        verify(todoService, times(1)).deleteTodo(999L);
    }
}
