package com.example.task_api.service;

import com.example.task_api.exception.InvalidTransitionException;
import com.example.task_api.exception.TaskNotFoundException;
import com.example.task_api.model.Task;
import com.example.task_api.model.TaskStatus;
import com.example.task_api.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    // Création d'une tâche
    public Task create(Task task) {

        if (task == null) {
            throw new IllegalArgumentException("La tâche est obligatoire");
        }

        if (task.getTitle() == null) {
            throw new IllegalArgumentException("Le titre est obligatoire");
        }

        String title = task.getTitle().trim();

        if (title.isEmpty() || title.length() > 120) {
            throw new IllegalArgumentException(
                    "Le titre doit contenir entre 1 et 120 caractères"
            );
        }

        task.setTitle(title);

        // Toute nouvelle tâche commence
        task.setStatus(TaskStatus.TODO);

        return repository.save(task);
    }

    // Liste des tâches
    public List<Task> getAll(TaskStatus status) {

        if (status == null) {
            return repository.findAll();
        }

        return repository.findByStatus(status);
    }

    // Modification du statut
    public Task updateStatus(Long id, TaskStatus newStatus) {

        Task task = repository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Tâche introuvable")
                );

        if (newStatus == null) {
            throw new IllegalArgumentException(
                    "Le statut est obligatoire"
            );
        }

        TaskStatus currentStatus = task.getStatus();

        if (!isValidTransition(currentStatus, newStatus)) {

            throw new InvalidTransitionException(
                    "Transition interdite : "
                            + currentStatus
                            + " -> "
                            + newStatus
            );
        }

        task.setStatus(newStatus);

        return repository.save(task);
    }

    private boolean isValidTransition(
            TaskStatus current,
            TaskStatus next) {

        return
                (current == TaskStatus.TODO
                        && next == TaskStatus.IN_PROGRESS)

                ||

                (current == TaskStatus.IN_PROGRESS
                        && next == TaskStatus.DONE);
    }
}