package com.prime.task_manager.repository;

import com.prime.task_manager.model.Task;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskRepository {
    private final Map<UUID, Task> storage = new HashMap<>();

    public List<Task> findAllTasks() {
        return new ArrayList<>(storage.values());
    }

    public Optional<Task> findTaskById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Task saveTask(Task task) {
        if (task.getId() == null) {
            task.setId(UUID.randomUUID());
            storage.put(task.getId(), task);
        }
        return task;
    }

    public void deleteTask(UUID id) {
        storage.remove(id);
    }

    public boolean doesTaskExist(UUID id) {
        return storage.containsKey(id);
    }
}
