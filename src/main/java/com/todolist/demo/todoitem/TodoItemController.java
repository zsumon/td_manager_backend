package com.todolist.demo.todoitem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoItemController {
    private static final String AUTH_HEADER = "Authorization";
    private final TodoItemService todoItemService;

    public TodoItemController(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoItem>> getCurrentUserTodos(@RequestHeader(name = AUTH_HEADER) String token) {
        return new ResponseEntity<>(todoItemService.getCurrentUserTodos(token.split(" ")[1]), HttpStatus.OK);
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoItem> addTodoItem(@RequestBody @Validated TodoItem todoItem, @RequestHeader(name = AUTH_HEADER) String authToken) {
        return new ResponseEntity<>(todoItemService.addTodoItem(todoItem, authToken.split(" ")[1]), HttpStatus.CREATED);
    }

    @PatchMapping("/todos")
    public ResponseEntity<TodoItem> updateTodoById(@RequestBody TodoItem todoItem) {
        return new ResponseEntity<>(todoItemService.updateTodo(todoItem), HttpStatus.OK);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable long id) {
        todoItemService.deleteTodoById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/public-todos")
    public ResponseEntity<List<TodoItem>> getPublicTodos() {
        return new ResponseEntity<>(todoItemService.getPublicTodos(), HttpStatus.OK);
    }
}
