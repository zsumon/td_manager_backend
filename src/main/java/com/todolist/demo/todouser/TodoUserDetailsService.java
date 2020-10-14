package com.todolist.demo.todouser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TodoUserDetailsService implements UserDetailsService {

    private final TodoUserService todoUserService;

    public TodoUserDetailsService(TodoUserService todoUserService) {
        this.todoUserService = todoUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // load user from database and return it
        TodoUser todoUser = todoUserService.getUserByUserName(s);
        if (todoUser == null) throw new UsernameNotFoundException("User not found with username: " + s);
        return new TodoUserDetails(todoUser.getUserName(), todoUser.getPassword());
    }
}
