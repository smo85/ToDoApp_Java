package org.example.todoapp.controller;

import org.example.todoapp.model.ToDoList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyFirstController {
  @GetMapping(value = "/todolist")
  public ToDoList getToDoList() {
    return ToDoList.builder().title("my-first-todo").build();
  }
}
