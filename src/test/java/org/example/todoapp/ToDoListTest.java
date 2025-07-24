package org.example.todoapp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import org.example.todoapp.model.ToDoItem;
import org.example.todoapp.model.ToDoList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToDoListTest {

  public static final String TITLE = "Test TODO List";
  public static final String DESCRIPTION = "My first TODO";
  public static final int ID = 1;
  public static final String TODO_TITLE = "Get milk";
  public static final int TODO_ID = 1;

  private ToDoList toDoList;
  private ToDoItem toDoItem;

  @BeforeEach
  void setUp() {
    toDoList =
        ToDoList.builder()
            .id(ID)
            .title(TITLE)
            .description(DESCRIPTION)
            .toDoItems(new ArrayList<>())
            .build();

    toDoItem = ToDoItem.builder().title(TODO_TITLE).id(TODO_ID).completed(false).build();
  }

  @Test
  void shouldCreateToDoList() {
    assertAll(
        () -> assertEquals(ID, toDoList.getId()),
        () -> assertEquals(TITLE, toDoList.getTitle()),
        () -> assertEquals(DESCRIPTION, toDoList.getDescription()),
        () -> assertEquals(List.of(), toDoList.getToDoItems()));
  }

  @Test
  void shouldCreateATodoItem() {
    assertAll(
        () -> assertEquals(TODO_TITLE, toDoItem.getTitle()),
        () -> assertEquals(TODO_ID, toDoItem.getId()),
        () -> assertFalse(toDoItem.getCompleted()),
        () -> assertNull(toDoItem.getDescription()));
  }

  @Test
  void shouldBeAbleToAddNewToDoItemToList() {
    toDoList.addTodo(toDoItem);
    assertAll(
        () -> assertEquals(1, toDoList.getToDoItems().size()),
        () -> assertEquals(toDoItem, toDoList.getToDoItems().get(0)));
  }
}
