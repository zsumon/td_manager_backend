package com.todolist.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // any return type, com.todolist.demo package or any of it's subpackage, any class ends in Service, any method name, any number of params of any type
    // selecting all methods of service classes
    @Before("execution(* com.todolist.demo..*Service.*(..))")
    public void test(JoinPoint joinPoint) {
        System.out.println("entering method: " + joinPoint.getSignature().getName());
    }
}
