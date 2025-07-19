package com.prime.task_manager.controller;

import com.prime.task_manager.model.Task;
import com.prime.task_manager.model.TaskStatus;
import com.prime.task_manager.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository repository;

    @BeforeEach
    void setUp() {
        repository.findAllTasks().forEach(task -> repository.deleteTask(task.getId()));
    }

    @Test
    void shouldCreateTask() throws Exception {
        Task task = new Task();
        task.setTitle("New Task");

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("New Task")))
                .andExpect(jsonPath("$.status", is("TODO")));
    }

    @Test
    void shouldGetAllTasks() throws Exception {
        Task task = new Task();
        task.setTitle("Task 1");
        repository.saveTask(task);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Task 1")));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        Task task = new Task();
        task.setTitle("Old");
        Task saved = repository.saveTask(task);

        Task updated = new Task();
        updated.setTitle("Updated");
        updated.setStatus(TaskStatus.DONE);

        mockMvc.perform(put("/tasks/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated")))
                .andExpect(jsonPath("$.status", is("DONE")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        Task task = new Task();
        task.setTitle("To Delete");
        Task saved = repository.saveTask(task);

        mockMvc.perform(delete("/tasks/" + saved.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistentTask() throws Exception {
        Task updated = new Task();
        updated.setTitle("Does not exist");
        updated.setStatus(TaskStatus.TODO);

        mockMvc.perform(put("/tasks/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound());
    }
}
