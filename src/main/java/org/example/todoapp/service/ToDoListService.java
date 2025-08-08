package org.example.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.todoapp.model.ToDoItem;
import org.example.todoapp.model.ToDoList;
import org.example.todoapp.repository.ToDoListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ToDoListService {
  private final ToDoListRepository toDoListRepository;

  public ToDoList createToDoList(ToDoList toDoList) {
    return toDoListRepository.save(toDoList);
  }

  public ToDoList getToDoListById(String listId) {
    return toDoListRepository.findById(listId).orElse(null);
  }

  @Transactional
  public ToDoList addNewToDoItem(String listId, ToDoItem toDoItem) {
    ToDoList toDoList = getToDoListById(listId);
    if (toDoList == null) {
      throw new NoSuchElementException("ToDoList with id " + listId + " not found");
    }
    toDoList.addTodo(toDoItem);
    return toDoList;
  }

  public void deleteToDoList(String listId) {
    toDoListRepository.deleteById(listId);
  }
}
