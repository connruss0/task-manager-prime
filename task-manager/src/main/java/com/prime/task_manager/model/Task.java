package com.prime.task_manager.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private UUID id;
    @NotBlank(message = "Title must not be blank")
    private String title;
    private String description;
    private TaskStatus status = TaskStatus.TODO;
}