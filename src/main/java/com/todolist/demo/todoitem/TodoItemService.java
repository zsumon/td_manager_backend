package com.todolist.demo.todoitem;

import com.todolist.demo.todouser.TodoUserService;
import com.todolist.demo.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemService {
    private final TodoItemRepository todoItemRepository;
    private final TodoUserService userService;
    private final JwtUtil jwtUtil;

    public TodoItemService(TodoItemRepository todoItemRepository, TodoUserService userService, JwtUtil jwtUtil) {
        this.todoItemRepository = todoItemRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public TodoItem addTodoItem(TodoItem todoItem, String token) {
        todoItem.setTodoUser(userService.getUserByUserName(jwtUtil.getUserNameFromToken(token)));
        return todoItemRepository.save(todoItem);
    }

    public List<TodoItem> getCurrentUserTodos(String token) {
        String userName = jwtUtil.getUserNameFromToken(token);
        return todoItemRepository.findAllByTodoUserUserNameOrderByIdDesc(userName);
    }

    public List<TodoItem> getPublicTodos() {
        return todoItemRepository.findAllByIsPublicIsTrueOrderByCreatedAtDesc();
    }

    public void deleteTodoById(long id) {
        todoItemRepository.deleteById(id);
    }

    public TodoItem updateTodo(TodoItem todoItem) {
        return todoItemRepository.save(todoItem);
    }
}
