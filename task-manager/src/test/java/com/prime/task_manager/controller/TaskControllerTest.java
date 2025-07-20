package com.prime.task_manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prime.task_manager.model.Task;
import com.prime.task_manager.model.TaskStatus;
import com.prime.task_manager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTask() throws Exception {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setStatus(TaskStatus.TODO);
        Task created = new Task();
        created.setId(UUID.randomUUID());
        created.setTitle("Test Task");
        created.setStatus(TaskStatus.TODO);

        Mockito.when(taskService.createTask(any(Task.class))).thenReturn(created);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.status").value("TODO"));
    }

    @Test
    void shouldGetAllTasks() throws Exception {
        Task task1 = new Task();
        task1.setId(UUID.randomUUID());
        task1.setTitle("Task 1");

        Task task2 = new Task();
        task2.setId(UUID.randomUUID());
        task2.setTitle("Task 2");

        Mockito.when(taskService.getAllTasks()).thenReturn(List.of(task1, task2));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldGetTaskById() throws Exception {
        UUID id = UUID.randomUUID();
        Task task = new Task();
        task.setId(id);
        task.setTitle("Read Email");

        Mockito.when(taskService.getTaskById(id)).thenReturn(task);

        mockMvc.perform(get("/tasks/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Read Email"));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        UUID id = UUID.randomUUID();
        Task updated = new Task();
        updated.setId(id);
        updated.setTitle("Updated Task");

        Mockito.when(taskService.updateTask(eq(id), any(Task.class))).thenReturn(updated);

        mockMvc.perform(put("/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/tasks/" + id))
                .andExpect(status().isOk());
    }
}
