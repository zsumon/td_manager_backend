package com.todolist.demo.todouser;

import com.todolist.demo.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodoUserController {
    private static final String AUTH_HEADER = "Authorization";
    private final TodoUserService todoUserService;
    private final JwtUtil jwtUtil;

    public TodoUserController(TodoUserService todoUserService, JwtUtil jwtUtil) {
        this.todoUserService = todoUserService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/user")
    public ResponseEntity<TodoUser> getCurrentUserProfile(@RequestHeader(AUTH_HEADER) String authToken) {
        return new ResponseEntity<>(todoUserService.getUserByUserName(jwtUtil.getUserNameFromToken(authToken.split(" ")[1])), HttpStatus.OK);
    }

    @GetMapping("/user?u={userName}")
    public ResponseEntity<TodoUser> getProfileByUserName(@PathVariable String userName) {
        return null;
    }
}
