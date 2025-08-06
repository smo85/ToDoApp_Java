package org.example.todoapp.controller;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.example.todoapp.model.ToDoItem;
import org.example.todoapp.model.ToDoList;
import org.example.todoapp.service.ToDoListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/to-do-lists")
@AllArgsConstructor
public class ToDoController {
  private final ToDoListService toDoListService;

  @GetMapping
  public ToDoList getToDoList() {
    return ToDoList.builder().title("my-first-todo").build();
  }

  @PostMapping
  public ToDoList createToDoList(@RequestBody ToDoList toDoList) {
    String id = UUID.randomUUID().toString();
    ToDoList newList =
        ToDoList.builder()
            .id(id)
            .title(toDoList.getTitle())
            .description(toDoList.getDescription())
            .build();
    return toDoListService.createToDoList(newList);
  }

  @PostMapping("{listId}/add-to-do")
  public ToDoList addNewToDoItem(@RequestBody ToDoItem toDoItem, @PathVariable String listId) {
    ToDoList list = toDoListService.getToDoListById(listId);
    list.addTodo(toDoItem);
    return list;
  }
}
