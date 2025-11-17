package com.aditya.todoApp.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;
    @NotBlank
    private String description;
    private boolean completed;

    public Todo(){

    }

    public Todo(String username, String description, boolean completed) {
        this.username = username;
        this.description = description;
        completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
