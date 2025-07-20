package com.prime.task_manager.service;

import com.prime.task_manager.exception.TaskNotFoundException;
import com.prime.task_manager.model.Task;
import com.prime.task_manager.model.TaskStatus;
import com.prime.task_manager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setup() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
    }

    @Test
    void shouldReturnAllTasks() {
        Task task1 = new Task();
        Task task2 = new Task();
        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnTaskById() {
        UUID id = UUID.randomUUID();
        Task task = new Task();
        task.setId(id);
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(id);

        assertEquals(id, result.getId());
        verify(taskRepository).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        UUID id = UUID.randomUUID();
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(id));
        verify(taskRepository).findById(id);
    }

    @Test
    void shouldCreateTask() {
        Task task = new Task();
        task.setTitle("Write Tests");

        when(taskRepository.save(task)).thenReturn(task);

        Task saved = taskService.createTask(task);

        assertEquals("Write Tests", saved.getTitle());
        verify(taskRepository).save(task);
    }

    @Test
    void shouldUpdateTask() {
        UUID id = UUID.randomUUID();
        Task existing = new Task();
        existing.setId(id);
        existing.setTitle("Old Title");

        Task updated = new Task();
        updated.setTitle("New Title");
        updated.setDescription("Updated Desc");
        updated.setStatus(TaskStatus.IN_PROGRESS);

        when(taskRepository.findById(id)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskService.updateTask(id, updated);

        assertEquals("New Title", result.getTitle());
        assertEquals("Updated Desc", result.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
        verify(taskRepository).findById(id);
        verify(taskRepository).save(existing);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentTask() {
        UUID id = UUID.randomUUID();
        Task updated = new Task();
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(id, updated));
    }

    @Test
    void shouldDeleteTaskById() {
        UUID id = UUID.randomUUID();
        when(taskRepository.existsById(id)).thenReturn(true);

        taskService.deleteTask(id);

        verify(taskRepository).existsById(id);
        verify(taskRepository).deleteById(id);
    }

    @Test
    void shouldThrowWhenDeletingNonExistentTask() {
        UUID id = UUID.randomUUID();
        when(taskRepository.existsById(id)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(id));
        verify(taskRepository, never()).deleteById(id);
    }
}
