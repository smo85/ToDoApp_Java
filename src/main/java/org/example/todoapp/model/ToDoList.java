package org.example.todoapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ToDoList {
  private static final AtomicInteger idCounter = new AtomicInteger(0);
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  private String title;
  private String description;
  @OneToMany(mappedBy = "toDoList", cascade = CascadeType.ALL, orphanRemoval = true)
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
