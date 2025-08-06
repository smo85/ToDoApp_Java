package org.example.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.todoapp.model.ToDoList;
import org.example.todoapp.repository.ToDoListRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToDoListService {
  private final ToDoListRepository toDoListRepository;

  public ToDoList createToDoList(ToDoList toDoList) {
    return toDoListRepository.save(toDoList);
  }

  public ToDoList getToDoListById(String listId) {
    return toDoListRepository
        .findById(listId)
        .orElseThrow(() -> new IllegalArgumentException("ToDoList not found: " + listId));
  }
}
