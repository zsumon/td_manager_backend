package com.todolist.demo.auth;

import com.todolist.demo.exception.UserAlreadyExistsException;
import com.todolist.demo.exception.WrongCredentialException;
import com.todolist.demo.todouser.TodoUser;
import com.todolist.demo.todouser.TodoUserDetailsService;
import com.todolist.demo.todouser.TodoUserService;
import com.todolist.demo.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final TodoUserService todoUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final TodoUserDetailsService todoUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(TodoUserService todoUserService,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          TodoUserDetailsService todoUserDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.todoUserService = todoUserService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.todoUserDetailsService = todoUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<TodoUser> register(@RequestBody @Validated RegRequest request) {
        TodoUser user;
        if (todoUserService.getUserByUserName(request.getUserName()) != null)
            throw new UserAlreadyExistsException("User already exists with user name " + request.getUserName());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user = todoUserService.addUser(new TodoUser(request.getUserName(), request.getFullName(), encodedPassword));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Validated AuthRequest authRequest) throws WrongCredentialException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        } catch (AuthenticationException exception) {
            throw new WrongCredentialException(exception.getMessage());
        }
        UserDetails userDetails = todoUserDetailsService.loadUserByUsername(authRequest.getUserName());
        String jsonWebToken = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jsonWebToken));
    }
}
