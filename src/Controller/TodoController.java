package com.aditya.todoApp.Controller;

import com.aditya.todoApp.Model.Todo;
import com.aditya.todoApp.Service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@Valid
@CrossOrigin("localhost:3000")
@ControllerAdvice

public class TodoController {
    @Autowired
    private TodoService service;

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo){
        // Get username from JWT (set in SecurityContext by JwtFilter)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        todo.setUsername(username); // Automatically set the logged-in user's username
        return service.addTodo(todo);
    }

    @GetMapping
    public List<Todo> getTodo(){
        return service.getTodo();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getById(@PathVariable Long id){
        Todo todo = service.getById(id);
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id , @RequestBody Todo todo){
        todo.setId(id);
        return ResponseEntity.ok(service.updateTodo(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id){
        service.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/user/{username}")
    public ResponseEntity<List<Todo>> getTodoByUsernameAndCompleted(@PathVariable String username , @RequestParam(required = false) Boolean completed){
        List<Todo> todos  ;
        if(completed != null){
            todos = service.getTodosByUsernameAndCompleted(username,completed);
        }else{
            todos = service.getTodosByUsername(username);
        }
        if(todos.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(todos);
        }
    }

    @GetMapping("/user/{username}/sorted")
    public ResponseEntity<List<Todo>> getUsernameSorted(@PathVariable String username , @RequestParam(defaultValue = "true") boolean newFirst){
        List<Todo> todos = service.getByUsernameSorted(username , newFirst);
        if(todos.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(todos);
        }
    }


}
