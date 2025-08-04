package org.example.todoapp.repository;

import org.example.todoapp.model.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepository extends JpaRepository<ToDoList, String> {}
