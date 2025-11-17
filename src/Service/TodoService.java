package com.aditya.todoApp.Service;


import com.aditya.todoApp.Exception.ResourceNotFoundException;
import com.aditya.todoApp.Model.Todo;
import com.aditya.todoApp.Repo.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repo;

//     create a todo
    public Todo addTodo(Todo todo){
        return repo.save(todo);
    }
//    fetch all data
    public List<Todo> getTodo(){
        return repo.findAll();
    }

    // fetch all the data by their id's
    public Todo getById(Long id){
        Optional<Todo> todo = repo.findById(id);
        if(todo.isPresent()){
            return todo.get();
        } else {
            throw new ResourceNotFoundException("Todo with id " + id + " not found");
        }
    }
    // update the data
    public Todo updateTodo(Todo todo){
        return repo.save(todo);
    }
    // delete the data
    public void deleteTodo(Long id ){
        repo.deleteById(id);
    }

    public List<Todo> getTodosByUsername(String username){
        return repo.findByUsername(username);
    }
    public List<Todo> getTodosByUsernameAndCompleted(String username, boolean completed){
        return repo.findByUsernameAndCompleted(username , completed);
    }

     public List<Todo> getByUsernameSorted(String username , boolean newFirst){
        if(newFirst){
            return repo.findByUsernameOrderByIdDesc(username);
        }else{
            return repo.findByUsernameOrderByIdAsc(username);
        }
     }







}
