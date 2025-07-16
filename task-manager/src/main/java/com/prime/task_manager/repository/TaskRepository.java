package com.prime.task_manager.repository;

import com.prime.task_manager.model.Task;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskRepository {
    /* The reason that I am using a HashMap here is to simulate a database.
       In a real-world application, this would be replaced with actual database operations.
       I have utilized a simple HashMap here to make it easier to run the application without needing a database setup.
       This is not a production-ready solution, but it serves well for demonstration and testing purposes.
       It would be simple to add a database to the docker container and pull the data from there instead of using a HashMap.
    */

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
