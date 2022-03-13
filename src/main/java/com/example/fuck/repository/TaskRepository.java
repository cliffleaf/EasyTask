package com.example.fuck.repository;

import com.example.fuck.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDateAndUserId(int date, long userId);
    void deleteTasksByName(String name);
    List<Task> findTasksByName(String name);
}
