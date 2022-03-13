package com.example.fuck.service;

import com.example.fuck.model.Task;
import com.example.fuck.model.User;
import com.example.fuck.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;

    public void save(Task task) {
        taskRepository.save(task);
    }

    public List<Task> findByDateAndUserId(int date, long userId) {
        // in testing, date=20220228, calendar=1
        return taskRepository.findByDateAndUserId(date, userId);
    }

    public Task getTask(long id) {
        return taskRepository.findById(id).get();
    }

    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    public void updateTaskName(long id, String name) {
        Task task = this.getTask(id);
        task.setName(name);
        this.save(task);
        // according to https://stackoverflow.com/questions/11881479/how-do-i-update-an-entity-using-spring-data-jpa
        // calling save() on an object with predefined id will update the corresponding database record rather than insert a new one
    }

    public long findUserId(String username) {
        User user = userServiceImpl.findUserByName(username);
        return user.getId();
    }

    public void updateIsComplete(long id, boolean isComplete) {
        Task task = this.getTask(id);
        task.setComplete(isComplete);
        this.save(task);
    }

    public void deleteTasksByName(String name) {
        taskRepository.deleteTasksByName(name);
    }

    public List<Task> findTasksByName(String name) {
        return taskRepository.findTasksByName(name);
    }
}
