package com.todolist.demo.todouser;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoUserRepository extends CrudRepository<TodoUser, Long> {
    TodoUser findByUserName(String userName);
}
