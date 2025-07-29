package org.example.todoapp.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.example.todoapp.model.ToDoList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ToDoControllerTest {

  public static final String TITLE = "Some Todo Title";
  public static final String DESCRIPTION = "some todo description";
  public static final String MOCK_UUID = "123e4567-e89b-12d3-a456-426614174000";

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
}
