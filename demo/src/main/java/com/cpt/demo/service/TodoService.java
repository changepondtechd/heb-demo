package com.cpt.demo.service;

import com.cpt.demo.dto.TodoRequestDto;
import com.cpt.demo.dto.TodoResponseDto;
import java.util.List;

public interface TodoService {

    /**
     * AC1: Create a new Todo Task
     * POST /api/todos
     */
    TodoResponseDto createTodo(TodoRequestDto requestDto);

    /**
     * AC2: Get a Todo Task by ID
     * GET /api/todos/{id}
     */
    TodoResponseDto getTodoById(Long id);

    /**
     * AC3: Get All Todo Tasks
     * GET /api/todos
     */
    List<TodoResponseDto> getAllTodos();

    /**
     * AC4: Update a Todo Task
     * PUT /api/todos/{id}
     */
    TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto);

    /**
     * AC5: Delete a Todo Task
     * DELETE /api/todos/{id}
     */
    void deleteTodo(Long id);
}
