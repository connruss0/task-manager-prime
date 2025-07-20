package com.prime.task_manager.repository;

import com.prime.task_manager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
}

