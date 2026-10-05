package com.example.task_api;

import com.example.task_api.model.Task;
import com.example.task_api.model.TaskStatus;
import com.example.task_api.repository.TaskRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc 
class TaskApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository repository;

    @BeforeEach
    void setUp() {

        repository.deleteAll();
    }

    // TEST 1 : création valide
    

    @Test
    void creationValide() throws Exception {

        String json = """
                {
                    "title": "Apprendre Spring Boot",
                    "description": "Créer une API REST"
                }
                """;

        mockMvc.perform(
                post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title")
                .value("Apprendre Spring Boot"))
        .andExpect(jsonPath("$.description")
                .value("Créer une API REST"))
        .andExpect(jsonPath("$.status")
                .value("TODO"));
    }


    // TEST 2 : titre invalide


    @Test
    void titreInvalide() throws Exception {

        String json = """
                {
                    "title": "   ",
                    "description": "Test"
                }
                """;

        mockMvc.perform(
                post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isBadRequest());
    }

  
    // TEST 3 : transition interdite


    @Test
    void transitionInterdite() throws Exception {

        // Création directe d'une tâche 
        Task task = new Task(
                "Apprendre Spring",
                "Test transition"
        );

        task.setStatus(TaskStatus.TODO);

        Task savedTask = repository.save(task);

        String json = """
                {
                    "status": "DONE"
                }
                """;

        mockMvc.perform(
                patch("/tasks/"
                        + savedTask.getId()
                        + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
        .andExpect(status().isConflict());
    }
}