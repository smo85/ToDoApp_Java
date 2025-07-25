package org.example.todoapp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToDoItem {
  private Integer id;
  private String title;
  private String description;
  private Boolean completed;
}
