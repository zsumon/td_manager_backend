package com.todolist.demo;

import com.todolist.demo.auth.AuthController;
import com.todolist.demo.auth.AuthRequest;
import com.todolist.demo.todoitem.TodoItem;
import com.todolist.demo.todoitem.TodoItemService;
import com.todolist.demo.todouser.TodoUser;
import com.todolist.demo.todouser.TodoUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class DemoApplicationTests {
    private final TodoItemService todoItemService;
    private final TodoUserService todoUserService;
    private final AuthController authController;

    @Autowired
    DemoApplicationTests(TodoItemService todoItemService, TodoUserService todoUserService, AuthController authController) {
        this.todoItemService = todoItemService;
        this.todoUserService = todoUserService;
        this.authController = authController;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void addTodos() {
        todoUserService.addUser(new TodoUser("user", "name", "pass"));
        var token = authController.authenticate(new AuthRequest("user", "pass")).getBody().getJwt();
        assertTrue("token obtained", token != null);
        for (int i = 0; i < 100; i++) {
            var todoItem = todoItemService.addTodoItem(new TodoItem("todo " + (int) (Math.random() * 10000)), token);
            assertTrue("successfully added new todo: " + (i * 10000), todoItem != null);
            assert todoItem != null;
            todoItem.setIsPublic(true);
            todoItemService.updateTodo(todoItem);
        }
    }
}
