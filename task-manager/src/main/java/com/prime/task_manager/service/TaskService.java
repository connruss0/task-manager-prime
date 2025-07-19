package com.prime.task_manager.service;

import com.prime.task_manager.exception.TaskNotFoundException;
import com.prime.task_manager.model.Task;
import com.prime.task_manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task createTask(Task task) {
        return repository.saveTask(task);
    }

    public List<Task> getAllTasks() {
        return repository.findAllTasks();
    }

    public Task getTaskById(UUID id) {
        return repository.findTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(UUID id, Task newTask) {
        Task existing = repository.findTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        existing.setTitle(newTask.getTitle());
        existing.setDescription(newTask.getDescription());
        existing.setStatus(newTask.getStatus());
        return repository.saveTask(existing);
    }

    public void deleteTask(UUID id) {
        if (!repository.doesTaskExist(id)) throw new TaskNotFoundException(id);
        repository.deleteTask(id);
    }
}
