package com.todolist.demo.todoitem;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {
    List<TodoItem> findAllByTodoUserUserNameOrderByIdDesc(String todoUserUserName);

    List<TodoItem> findAllByIsDoneIsTrue();

    List<TodoItem> findAllByIsPublicIsTrueOrderByCreatedAtDesc();
}
