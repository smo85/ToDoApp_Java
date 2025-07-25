package org.example.todoapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToDoList {
  private static final AtomicInteger idCounter = new AtomicInteger(0);
  private Integer id;
  private String title;
  private String description;
  private List<ToDoItem> toDoItems;

  public void addTodo(ToDoItem toDoItem) {
    toDoItem.setId(idCounter.incrementAndGet());
    if (toDoItems == null) {
      toDoItems = new ArrayList<>();
    }
    toDoItems.add(toDoItem);
  }

  public ToDoItem getTodoItemById(Integer id) {
    if (toDoItems != null) {
      for (ToDoItem toDoItem : toDoItems) {
        if (toDoItem.getId().equals(id)) {
          return toDoItem;
        }
      }
    }
    return null;
  }

  public Boolean deleteTodoItemById(Integer id) {
    if (toDoItems != null) {
      return toDoItems.removeIf(toDoItem -> toDoItem.getId().equals(id));
    }
    return false;
  }
}
