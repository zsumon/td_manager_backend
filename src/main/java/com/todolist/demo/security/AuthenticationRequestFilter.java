package com.todolist.demo.security;

import com.todolist.demo.todouser.TodoUserDetailsService;
import com.todolist.demo.util.JwtUtil;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class AuthenticationRequestFilter extends OncePerRequestFilter {
    private static final String AUTH_HEADER = "Authorization";
    private final TodoUserDetailsService todoUserDetailsService;
    private final JwtUtil jwtUtil;

    public AuthenticationRequestFilter(TodoUserDetailsService todoUserDetailsService, JwtUtil jwtUtil) {
        this.todoUserDetailsService = todoUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(AUTH_HEADER);
        String userName = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.split(" ")[1];
            userName = jwtUtil.getUserNameFromToken(token);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println(authentication.getPrincipal());
            throw new RuntimeException("authentication is not null!!!");
        }

        // why do I need to check whether the authentication is null or not ... if each request is stateless then it should be null each time, isn't it??
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //// manually simulating the authentication process
            UserDetails userDetails = todoUserDetailsService.loadUserByUsername(userName);
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); /*----*/
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                // we need to set principal in the security context to let others (filters, whole system, know about the authenticated user)
                // request goes deeper (way to the controller through other filters),  if we don't set principal in the context further actions will be treated as unauthenticated
            }
        }
        filterChain.doFilter(request, response);
    }
}
