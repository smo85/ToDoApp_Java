package org.example.todoapp.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToDoListTest {

  public static final String TITLE = "Test TODO List";
  public static final String DESCRIPTION = "My first TODO";
  public static final String ID = "someId";
  public static final String TODO_TITLE = "Get milk";

  private ToDoList toDoList;

  private static void createSomeTodos(int numberOfToDoItems, ToDoList toDoList) {
    for (int i = 0; i < numberOfToDoItems; i++) {
      ToDoItem toDoItem = createTodoItem(TODO_TITLE + (i + 1));
      toDoList.addTodo(toDoItem);
    }
  }

  private static ToDoItem createTodoItem(String title) {
    return ToDoItem.builder().title(title).build();
  }

  @BeforeEach
  void setUp() {
    toDoList =
        ToDoList.builder()
            .id(ID)
            .title(TITLE)
            .description(DESCRIPTION)
            .toDoItems(new ArrayList<>())
            .build();
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
    ToDoItem toDoItem = createTodoItem(TODO_TITLE);
    assertAll(
        () -> assertEquals(TODO_TITLE, toDoItem.getTitle()),
        () -> assertFalse(toDoItem.getCompleted()),
        () -> assertNull(toDoItem.getDescription()),
        () -> assertFalse(toDoItem.getCompleted()));
  }

  @Test
  void shouldCreateUniqueIdForNewToDoItem() {
    createSomeTodos(3, toDoList);
    List<ToDoItem> toDoItems = toDoList.getToDoItems();
    Set<Integer> uniqueIds = toDoItems.stream().map(ToDoItem::getId).collect(Collectors.toSet());
    assertEquals(toDoItems.size(), uniqueIds.size());
  }

  @Test
  void shouldBeAbleToAddNewToDoItemToList() {
    ToDoList newList = ToDoList.builder().build();
    ToDoItem toDoItem = createTodoItem(TODO_TITLE);
    newList.addTodo(toDoItem);
    assertAll(
        () -> assertEquals(1, newList.getToDoItems().size()),
        () -> assertEquals(toDoItem, newList.getToDoItems().getFirst()));
  }

  @Test
  void shouldDeleteToDoItems() {
    int numberOfTodos = 3;
    int idToDelete = 2;
    createSomeTodos(numberOfTodos, toDoList);

    Boolean wasDeleted = toDoList.deleteTodoItemById(idToDelete);
    assertTrue(wasDeleted);
    assertEquals(numberOfTodos - 1, toDoList.getToDoItems().size());
    assertNull(toDoList.getTodoItemById(idToDelete));
  }
}
