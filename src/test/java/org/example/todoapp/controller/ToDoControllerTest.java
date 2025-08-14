package org.example.todoapp.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.example.todoapp.model.ToDoItem;
import org.example.todoapp.model.ToDoList;
import org.example.todoapp.repository.ToDoListRepository;
import org.example.todoapp.service.ToDoListService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ToDoControllerTest {
  public static final String TITLE = "Some Todo Title";
  public static final String DESCRIPTION = "some todo description";
  @Autowired private ToDoListRepository toDoListRepository;
  @Autowired private ToDoListService toDoListService;

  @AfterEach
  void setup() {
    toDoListRepository.deleteAll();
  }

  @Test
  void shouldReturnToDoList() {
    ToDoList newTodoList = ToDoList.builder().title(TITLE).description(DESCRIPTION).build();
    toDoListRepository.save(newTodoList);

    ToDoList response =
        given()
            .when()
            .get("/to-do-lists/{id}", newTodoList.getId())
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(ToDoList.class);
    assertNotNull(response);
  }

  @Test
  void shouldCreateNewToDoList() {
    ToDoList newTodoList = ToDoList.builder().title(TITLE).description(DESCRIPTION).build();
    ToDoList response =
        given()
            .body(newTodoList)
            .contentType("application/json")
            .when()
            .post("/to-do-lists")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(ToDoList.class);

    assertNotNull(response.getId());
    assertEquals(newTodoList.getTitle(), response.getTitle());
    assertEquals(newTodoList.getDescription(), response.getDescription());
    assertEquals(List.of(), response.getToDoItems());
  }

  @Test
  void shouldAddANewToDoItem() {
    ToDoList newTodoList = ToDoList.builder().title(TITLE).description(DESCRIPTION).build();
    toDoListRepository.save(newTodoList);

    ToDoItem newToDoItem = ToDoItem.builder().title("Get some milk").build();
    ToDoList response =
        given()
            .body(newToDoItem)
            .contentType("application/json")
            .when()
            .post("/to-do-lists/{id}/add-to-do", newTodoList.getId())
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(ToDoList.class);

    assertEquals(newTodoList.getId(), response.getId());
    List<ToDoItem> toDoItems = response.getToDoItems();
    assertFalse(toDoItems.isEmpty(), "Expected at least one ToDoItem in the list");
    ToDoItem addedToDoItem = toDoItems.getFirst();
    assertEquals(addedToDoItem.getTitle(), newToDoItem.getTitle());
  }

  @Test
  void shouldDeleteToDoList() {
    ToDoList newTodoList = ToDoList.builder().title(TITLE).description(DESCRIPTION).build();
    toDoListRepository.save(newTodoList);

    given()
        .when()
        .post("/to-do-lists/{id}/delete-to-do-list", newTodoList.getId())
        .then()
        .statusCode(200);

    assertNull(toDoListService.getToDoListById(newTodoList.getId()));
  }

  @Test
  void shouldThrowErrorIfNoToDoListFound() {
    ToDoList newTodoList = ToDoList.builder().title(TITLE).description(DESCRIPTION).build();
    toDoListRepository.save(newTodoList);

    ToDoItem newToDoItem = ToDoItem.builder().title("Get some milk").build();
    String invalidId = "some-id-that-does-not-exist";

    given()
        .body(newToDoItem)
        .contentType("application/json")
        .when()
        .post("/to-do-lists/{id}/add-to-do", invalidId)
        .then()
        .statusCode(500);
    // TODO - add more robust error handling
  }

  @Test
  void shouldReturnAllToDoLists() {
    ToDoList newTodoList1 = ToDoList.builder().title(TITLE + "1").description(DESCRIPTION).build();
    ToDoList newTodoList2 = ToDoList.builder().title(TITLE + "2").description(DESCRIPTION).build();
    ToDoList newTodoList3 = ToDoList.builder().title(TITLE + "3").description(DESCRIPTION).build();
    toDoListRepository.save(newTodoList1);
    toDoListRepository.save(newTodoList2);
    toDoListRepository.save(newTodoList3);

    List<ToDoList> response =
        given()
            .when()
            .get("/to-do-lists")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getList(".", ToDoList.class);

    assertEquals(3, response.size());
    toDoListRepository.delete(newTodoList1);
    toDoListRepository.delete(newTodoList2);
    toDoListRepository.delete(newTodoList3);
  }

  // shouldDeleteToDoItemFromList
  // shouldCompleteToDoItem
}
