package com.cpt.demo.dto;

import com.cpt.demo.entity.TodoPriority;
import com.cpt.demo.entity.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDto {

    private Long id;

    private String title;

    private String description;

    private TodoStatus status;

    private TodoPriority priority;

    private LocalDate dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
