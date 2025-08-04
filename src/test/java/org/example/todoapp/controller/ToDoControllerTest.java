package org.example.todoapp.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.example.todoapp.model.ToDoItem;
import org.example.todoapp.model.ToDoList;
import org.example.todoapp.repository.ToDoListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ToDoControllerTest {
  @Autowired private ToDoListRepository toDoListRepository;

  public static final String TITLE = "Some Todo Title";
  public static final String DESCRIPTION = "some todo description";

  @Test
  void shouldReturnToDoList() {
    ToDoList response =
        given()
            .when()
            .get("/to-do-lists")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .as(ToDoList.class);
    System.out.println(response);
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
    assertNull(response.getToDoItems());
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
    ToDoItem addedToDoItem = response.getToDoItems().getFirst();
    assertEquals(addedToDoItem.getTitle(), newToDoItem.getTitle());
  }
}
