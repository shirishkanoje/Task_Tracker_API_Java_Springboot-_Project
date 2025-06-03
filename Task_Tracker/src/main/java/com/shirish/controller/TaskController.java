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

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // Create task (with userId passed in request param for simplicity)
    @PostMapping
    public ResponseEntity<?> createTask(@RequestParam Long userId, @RequestBody @Valid Task task) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }
        task.setUser(user.get());
        Task saved = taskRepository.save(task);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // Other endpoints (GET all, GET by id, PUT, DELETE) remain similar, but you can add user checks if needed

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody @Valid Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setStatus(updatedTask.getStatus());
            Task saved = taskRepository.save(task);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.ok("Task deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
    }


    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable String status) {
        return taskRepository.findByStatus(Enum.valueOf(TaskStatus.class, status.toUpperCase()));
    }

    @GetMapping("/due/{dueDate}")
    public List<Task> getTasksByDueDate(@PathVariable String dueDate) {
        LocalDate date = LocalDate.parse(dueDate);
        return taskRepository.findByDueDate(date);
    }
}
