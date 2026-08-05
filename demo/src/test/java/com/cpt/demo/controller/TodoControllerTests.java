package com.cpt.demo.controller;

import com.cpt.demo.dto.TodoRequestDto;
import com.cpt.demo.dto.TodoResponseDto;
import com.cpt.demo.entity.TodoPriority;
import com.cpt.demo.entity.TodoStatus;
import com.cpt.demo.exception.TodoNotFoundException;
import com.cpt.demo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    private TodoRequestDto requestDto;
    private TodoResponseDto responseDto;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();

        requestDto = new TodoRequestDto();
        requestDto.setTitle("Test Todo");
        requestDto.setDescription("Test Description");
        requestDto.setStatus("TODO");
        requestDto.setPriority("HIGH");
        requestDto.setDueDate(LocalDate.now().plusDays(1));

        responseDto = new TodoResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Test Todo");
        responseDto.setDescription("Test Description");
        responseDto.setStatus(TodoStatus.TODO);
        responseDto.setPriority(TodoPriority.HIGH);
        responseDto.setDueDate(LocalDate.now().plusDays(1));
        responseDto.setCreatedAt(now);
        responseDto.setUpdatedAt(now);
    }

    // ============ AC1: Create a Todo Task ============

    @Test
    void testCreateTodo_HappyPath_ReturnsCreated() throws Exception {
        when(todoService.createTodo(any(TodoRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Todo"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.status").value("TODO"))
                .andExpect(jsonPath("$.priority").value("HIGH"))
                .andExpect(jsonPath("$.dueDate").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());

        verify(todoService).createTodo(any(TodoRequestDto.class));
    }

    @Test
    void testCreateTodo_MissingTitle_ReturnsBadRequest() throws Exception {
        TodoRequestDto invalidDto = new TodoRequestDto();
        invalidDto.setTitle(null);
        invalidDto.setStatus("TODO");
        invalidDto.setPriority("HIGH");

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").exists());
    }

    // ============ AC2: Get a Todo Task by ID ============

    @Test
    void testGetTodoById_HappyPath_ReturnsOk() throws Exception {
        when(todoService.getTodoById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Todo"))
                .andExpect(jsonPath("$.status").value("TODO"))
                .andExpect(jsonPath("$.priority").value("HIGH"));

        verify(todoService).getTodoById(1L);
    }

    @Test
    void testGetTodoById_NotFound_Returns404() throws Exception {
        when(todoService.getTodoById(999L))
                .thenThrow(new TodoNotFoundException("Todo with id 999 not found"));

        mockMvc.perform(get("/api/todos/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Todo with id 999 not found"));

        verify(todoService).getTodoById(999L);
    }

    // ============ AC3: Get All Todo Tasks ============

    @Test
    void testGetAllTodos_HappyPath_ReturnsOkWithList() throws Exception {
        List<TodoResponseDto> todos = Arrays.asList(responseDto);
        when(todoService.getAllTodos()).thenReturn(todos);

        mockMvc.perform(get("/api/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Todo"));

        verify(todoService).getAllTodos();
    }

    @Test
    void testGetAllTodos_EmptyList_ReturnsOkWithEmptyArray() throws Exception {
        when(todoService.getAllTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(todoService).getAllTodos();
    }

    // ============ AC4: Update a Todo Task ============

    @Test
    void testUpdateTodo_HappyPath_ReturnsOk() throws Exception {
        TodoResponseDto updatedDto = new TodoResponseDto();
        updatedDto.setId(1L);
        updatedDto.setTitle("Updated Todo");
        updatedDto.setDescription("Updated Description");
        updatedDto.setStatus(TodoStatus.IN_PROGRESS);
        updatedDto.setPriority(TodoPriority.MEDIUM);
        updatedDto.setDueDate(LocalDate.now().plusDays(5));
        updatedDto.setCreatedAt(now);
        updatedDto.setUpdatedAt(LocalDateTime.now());

        TodoRequestDto updateDto = new TodoRequestDto();
        updateDto.setTitle("Updated Todo");
        updateDto.setDescription("Updated Description");
        updateDto.setStatus("IN_PROGRESS");
        updateDto.setPriority("MEDIUM");
        updateDto.setDueDate(LocalDate.now().plusDays(5));

        when(todoService.updateTodo(eq(1L), any(TodoRequestDto.class))).thenReturn(updatedDto);

        mockMvc.perform(put("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Todo"))
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));

        verify(todoService).updateTodo(eq(1L), any(TodoRequestDto.class));
    }

    @Test
    void testUpdateTodo_NotFound_Returns404() throws Exception {
        when(todoService.updateTodo(eq(999L), any(TodoRequestDto.class)))
                .thenThrow(new TodoNotFoundException("Todo with id 999 not found"));

        mockMvc.perform(put("/api/todos/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Todo with id 999 not found"));

        verify(todoService).updateTodo(eq(999L), any(TodoRequestDto.class));
    }

    // ============ AC5: Delete a Todo Task ============

    @Test
    void testDeleteTodo_HappyPath_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(todoService).deleteTodo(1L);
    }

    @Test
    void testDeleteTodo_NotFound_Returns404() throws Exception {
        doThrow(new TodoNotFoundException("Todo with id 999 not found"))
                .when(todoService).deleteTodo(999L);

        mockMvc.perform(delete("/api/todos/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Todo with id 999 not found"));

        verify(todoService).deleteTodo(999L);
    }
}
