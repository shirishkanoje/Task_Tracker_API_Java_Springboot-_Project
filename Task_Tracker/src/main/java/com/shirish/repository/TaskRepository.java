package com.shirish.repository;

import com.shirish.modal.Task;
import com.shirish.domain.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByDueDate(LocalDate dueDate);
}
