package com.shirish.repository;

import com.shirish.modal.Task;
import com.shirish.domain.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing Task entities.
 * Provides built-in CRUD operations and custom queries.
 **/
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByDueDate(LocalDate dueDate);
}
