package com.shirish.controller;

import com.shirish.domain.TaskStatus;
import com.shirish.modal.Task;
import com.shirish.modal.User;
import com.shirish.repository.TaskRepository;
import com.shirish.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Controller for managing task-related operations.
 * Provides endpoints to create, retrieve, update, delete,
 * and filter tasks by status or due date.

 * Author: Shirish Kanoje
 * Last Modified: 28 June 2025
 **/
@RestController
@RequestMapping("/tasks")
public class TaskController {

    /** Repository for task-related database operations */
    @Autowired
    private TaskRepository taskRepository;

    /** Repository for user-related database operations */
    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new task and associate it with a user.
     *
     * @param userId ID of the user
     * @param task   Task details
     * @return HTTP 201 with saved task or 400 if user not found
     */
    @PostMapping
    public ResponseEntity<?> createTask(@RequestParam Long userId, @RequestBody @Valid Task task) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }

        task.setUser(user.get());
        Task saved = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Retrieve all tasks.
     *
     * @return list of all tasks
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Retrieve a task by its ID.
     *
     * @param id task ID
     * @return task if found, 404 otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update a task by its ID.
     *
     * @param id          task ID
     * @param updatedTask new task details
     * @return updated task if found, 404 otherwise
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody @Valid Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setStatus(updatedTask.getStatus());
            Task saved = taskRepository.save(task);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a task by its ID.
     *
     * @param id task ID
     * @return success or not-found message
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.ok("Task deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
    }

    /**
     * Get all tasks with a specific status.
     *
     * @param status status string (e.g., "PENDING")
     * @return list of tasks with matching status
     */
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable String status) {
        return taskRepository.findByStatus(Enum.valueOf(TaskStatus.class, status.toUpperCase()));
    }

    /**
     * Get all tasks due on a specific date.
     *
     * @param dueDate the due date in YYYY-MM-DD format
     * @return list of tasks due on that date
     */
    @GetMapping("/due/{dueDate}")
    public List<Task> getTasksByDueDate(@PathVariable String dueDate) {
        LocalDate date = LocalDate.parse(dueDate);
        return taskRepository.findByDueDate(date);
    }
}
