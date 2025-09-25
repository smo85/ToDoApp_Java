package org.example.todoapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ToDoList {
  @Id @Builder.Default private String id = UUID.randomUUID().toString();
  private String title;
  private String description;

  @Builder.Default
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "todo_list_id")
  private List<ToDoItem> toDoItems = new ArrayList<>();

  public void addTodo(ToDoItem newToDoItem) {
    if (toDoItems == null) {
      toDoItems = new ArrayList<>();
    }
    this.toDoItems.add(newToDoItem);
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

//  public void updateTodoItem(ToDoItem toDoItem, ToDoList toDoList, int itemId) {
//    if(toDoList.getTodoItemById(itemId) != null) {
//      toDoList.
//    }
//
//  }
}
