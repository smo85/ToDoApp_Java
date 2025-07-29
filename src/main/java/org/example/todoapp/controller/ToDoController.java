package org.example.todoapp.controller;

import java.util.UUID;
import org.example.todoapp.model.ToDoList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/to-do-lists")
public class ToDoController {
  @GetMapping
  public ToDoList getToDoList() {
    return ToDoList.builder().title("my-first-todo").build();
  }

  @PostMapping
  public ToDoList createToDoList(@RequestBody ToDoList toDoList) {
    String id = UUID.randomUUID().toString();
    return ToDoList.builder()
        .id(id)
        .title(toDoList.getTitle())
        .description(toDoList.getDescription())
        .build();
  }
}
