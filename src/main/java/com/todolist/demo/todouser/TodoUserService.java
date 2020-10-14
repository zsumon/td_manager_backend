package com.todolist.demo.todouser;

import org.springframework.stereotype.Service;

@Service
public class TodoUserService {
    private final TodoUserRepository repository;

    public TodoUserService(TodoUserRepository repository) {
        this.repository = repository;
    }

    public TodoUser addUser(TodoUser todoUser) {
        // validate the todoUser, check name, username, password if they are empty
        return repository.save(todoUser);
    }

    public TodoUser getUserByUserName(String userName) {
        return repository.findByUserName(userName);
    }
}
