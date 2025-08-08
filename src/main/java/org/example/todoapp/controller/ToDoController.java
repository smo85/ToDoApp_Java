package org.example.todoapp.controller;

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

  @GetMapping({"{listId}"})
  public ToDoList getToDoList(@PathVariable String listId) {
    return toDoListService.getToDoListById(listId);
  }

  @PostMapping
  public ToDoList createToDoList(@RequestBody ToDoList toDoList) {
    ToDoList newList =
        ToDoList.builder()
            .title(toDoList.getTitle())
            .description(toDoList.getDescription())
            .build();
    return toDoListService.createToDoList(newList);
  }

  @PostMapping("{listId}/add-to-do")
  public ToDoList addNewToDoItem(@RequestBody ToDoItem toDoItem, @PathVariable String listId) {
    return toDoListService.addNewToDoItem(listId, toDoItem);
  }

  @PostMapping("{listId}/delete-to-do-list")
  public void deleteToDoList(@PathVariable String listId) {
    toDoListService.deleteToDoList(listId);
  }
}
