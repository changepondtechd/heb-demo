package com.cpt.demo.service;

import com.cpt.demo.dto.TodoRequestDto;
import com.cpt.demo.dto.TodoResponseDto;
import com.cpt.demo.entity.Todo;
import com.cpt.demo.entity.TodoPriority;
import com.cpt.demo.entity.TodoStatus;
import com.cpt.demo.exception.TodoNotFoundException;
import com.cpt.demo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    /**
     * AC1: Create a new Todo Task
     */
    @Override
    public TodoResponseDto createTodo(TodoRequestDto requestDto) {
        Todo todo = new Todo();
        todo.setTitle(requestDto.getTitle());
        todo.setDescription(requestDto.getDescription());
        todo.setStatus(TodoStatus.valueOf(requestDto.getStatus().toUpperCase()));
        todo.setPriority(TodoPriority.valueOf(requestDto.getPriority().toUpperCase()));
        todo.setDueDate(requestDto.getDueDate());

        Todo savedTodo = todoRepository.save(todo);
        return mapToResponseDto(savedTodo);
    }

    /**
     * AC2: Get a Todo Task by ID
     */
    @Override
    public TodoResponseDto getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo with id " + id + " not found"));
        return mapToResponseDto(todo);
    }

    /**
     * AC3: Get All Todo Tasks
     */
    @Override
    public List<TodoResponseDto> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * AC4: Update a Todo Task
     */
    @Override
    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo with id " + id + " not found"));

        todo.setTitle(requestDto.getTitle());
        todo.setDescription(requestDto.getDescription());
        todo.setStatus(TodoStatus.valueOf(requestDto.getStatus().toUpperCase()));
        todo.setPriority(TodoPriority.valueOf(requestDto.getPriority().toUpperCase()));
        todo.setDueDate(requestDto.getDueDate());

        Todo updatedTodo = todoRepository.save(todo);
        return mapToResponseDto(updatedTodo);
    }

    /**
     * AC5: Delete a Todo Task
     */
    @Override
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException("Todo with id " + id + " not found");
        }
        todoRepository.deleteById(id);
    }

    /**
     * Map Todo entity to TodoResponseDto
     */
    private TodoResponseDto mapToResponseDto(Todo todo) {
        return new TodoResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getStatus(),
                todo.getPriority(),
                todo.getDueDate(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}
