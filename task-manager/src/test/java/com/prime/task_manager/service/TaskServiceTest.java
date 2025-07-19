package com.prime.task_manager.service;

import com.prime.task_manager.exception.TaskNotFoundException;
import com.prime.task_manager.model.Task;
import com.prime.task_manager.model.TaskStatus;
import com.prime.task_manager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {
    private TaskService service;

    @BeforeEach
    void setup() {
        service = new TaskService(new TaskRepository());
    }

    @Test
    void createAndRetrieveTask() {
        Task task = new Task();
        task.setTitle("Test");
        Task saved = service.createTask(task);
        assertEquals("Test", service.getAllTasks().getFirst().getTitle());
    }

    @Test
    void updateTaskSuccessfully() {
        Task task = new Task();
        task.setTitle("Original");
        Task saved = service.createTask(task);

        Task updated = new Task();
        updated.setTitle("Updated");
        updated.setDescription("Updated description");
        updated.setStatus(TaskStatus.IN_PROGRESS);

        Task result = service.updateTask(saved.getId(), updated);

        assertEquals("Updated", result.getTitle());
        assertEquals("Updated description", result.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
    }

    @Test
    void deleteTaskSuccessfully() {
        Task task = new Task();
        task.setTitle("To be deleted");
        Task saved = service.createTask(task);

        service.deleteTask(saved.getId());

        assertTrue(service.getAllTasks().isEmpty());
    }

    @Test
    void updateNonExistentTaskThrowsException() {
        UUID fakeId = UUID.randomUUID();
        Task updated = new Task();
        updated.setTitle("Should fail");
        updated.setStatus(TaskStatus.TODO);

        assertThrows(TaskNotFoundException.class, () -> service.updateTask(fakeId, updated));
    }

    @Test
    void deleteNonExistentTaskThrowsException() {
        UUID fakeId = UUID.randomUUID();
        assertThrows(TaskNotFoundException.class, () -> service.deleteTask(fakeId));
    }
}
