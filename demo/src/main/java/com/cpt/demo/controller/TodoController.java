package com.cpt.demo.controller;

import com.cpt.demo.dto.TodoRequestDto;
import com.cpt.demo.dto.TodoResponseDto;
import com.cpt.demo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TodoController {

    private final TodoService todoService;

    /**
     * AC1: Create a Todo Task
     * POST /api/todos
     * Returns: 201 Created with TodoResponseDto
     */
    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@Valid @RequestBody TodoRequestDto requestDto) {
        TodoResponseDto response = todoService.createTodo(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * AC2: Get a Todo Task by ID
     * GET /api/todos/{id}
     * Returns: 200 OK with TodoResponseDto or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable Long id) {
        TodoResponseDto response = todoService.getTodoById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * AC3: Get All Todo Tasks
     * GET /api/todos
     * Returns: 200 OK with List<TodoResponseDto>
     */
    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        List<TodoResponseDto> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    /**
     * AC4: Update a Todo Task
     * PUT /api/todos/{id}
     * Returns: 200 OK with updated TodoResponseDto or 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoRequestDto requestDto) {
        TodoResponseDto response = todoService.updateTodo(id, requestDto);
        return ResponseEntity.ok(response);
    }

    /**
     * AC5: Delete a Todo Task
     * DELETE /api/todos/{id}
     * Returns: 204 No Content or 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
