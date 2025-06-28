package com.shirish.service;

import com.shirish.domain.TaskStatus;
import com.shirish.modal.Task;
import com.shirish.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling task-related business logic.
 * Provides CRUD operations and filtering support for tasks.
 *
 * Author: Shirish Kanoje
 * Last Updated: 28 June 2025
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Creates and saves a new task.
     *
     * @param task the task to be saved
     * @return the saved Task entity
     */
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Retrieves all tasks.
     *
     * @return list of all tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id task ID
     * @return Optional containing the task, if found
     */
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Updates an existing task.
     *
     * @param id          task ID
     * @param updatedTask updated task data
     * @return the updated Task, or null if not found
     */
    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setStatus(updatedTask.getStatus());
            return taskRepository.save(task);
        }).orElse(null);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id task ID
     */
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    /**
     * Retrieves tasks by their status.
     *
     * @param status task status (e.g., PENDING, COMPLETED)
     * @return list of tasks matching the status
     */
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    /**
     * Retrieves tasks by their due date.
     *
     * @param dueDate due date to match
     * @return list of tasks due on the specified date
     */
    public List<Task> getTasksByDueDate(LocalDate dueDate) {
        return taskRepository.findByDueDate(dueDate);
    }
}
