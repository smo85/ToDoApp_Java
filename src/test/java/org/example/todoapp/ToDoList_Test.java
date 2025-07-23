package org.example.todoapp;

import org.example.todoapp.model.ToDoList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToDoList_Test {

  public static final String TITLE = "Test TODO List";
  public static final String DESCRIPTION = "My first TODO";
  public static final int ID = 1;

  @Test
  void shouldCreateToDoList() {
    ToDoList toDoList = ToDoList.builder().id(ID).title(TITLE).description(DESCRIPTION).build();
    Assertions.assertEquals(ID, toDoList.getId());
    Assertions.assertEquals(TITLE, toDoList.getTitle());
    Assertions.assertEquals(DESCRIPTION, toDoList.getDescription());
  }
}
