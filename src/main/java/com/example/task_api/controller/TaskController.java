package com.example.task_api.controller;

import com.example.task_api.model.Task;
import com.example.task_api.model.TaskStatus;
import com.example.task_api.service.TaskService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@Valid @RequestBody Task task) {
        return service.create(task);
    }

    @GetMapping
    public List<Task> getAll(
            @RequestParam(required = false) TaskStatus status) {

        return service.getAll(status);
    }

    @PatchMapping("/{id}/status")
    public Task updateStatus(
            @PathVariable Long id,
            @RequestBody StatusRequest request) {

        return service.updateStatus(id, request.status());
    }

    public record StatusRequest(TaskStatus status) {
    }
}