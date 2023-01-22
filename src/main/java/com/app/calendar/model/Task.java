package com.app.calendar.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "task")
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private int date;

    @Column(name = "description")
    private String description;

    @Column(name = "is_complete")
    private boolean isComplete;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "type")
    private int type; // 0 for revision, 1 for assessment

    public Task(String name, int date, String description, boolean isComplete, long userId, int type) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.isComplete = isComplete;
        this.userId = userId;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", isComplete=" + isComplete +
                ", userId=" + userId +
                ", type=" + type +
                '}';
    }
}
