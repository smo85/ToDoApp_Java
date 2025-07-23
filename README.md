# ToDo List App

A simple RESTful ToDo List application built with Java, Spring Boot, and JUnit. This project is designed to help you practice core Java, Spring Boot, and TDD fundamentals.

## Features

- Add, update, delete, and list to-do items
- RESTful API endpoints
- In-memory or persistent storage (configurable)
- Unit and integration tests with JUnit

## Getting Started

### Prerequisites

- Java 21
- Maven

### Setup

1. Clone the repository:
   ```
   git clone https://github.com/smo85/ToDoApp_Java.git
   cd ToDoApp_Java
   ```

2. Build the project:
   ```
   mvn clean install
   ```

3. Run the application:
   ```
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080`.

## API Endpoints - TBD

- `GET /todos` — List all to-do items
- `POST /todos` — Create a new to-do item
- `PUT /todos/{id}` — Update an existing to-do item
- `DELETE /todos/{id}` — Delete a to-do item

## Running Tests

To run all tests:
```
mvn test
```

## Contributing

Contributions are welcome! Please open issues or submit pull requests for improvements.

## License

TBD
