package org.example.todoapp.controller;

import static io.restassured.RestAssured.given;

import org.example.todoapp.model.ToDoList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MyFirstControllerTest {

  @Test
  void firstControllerTest() {
    ToDoList response =
        given().when().get("/todolist").then().statusCode(200).extract().body().as(ToDoList.class);
    System.out.println(response);
  }
}
