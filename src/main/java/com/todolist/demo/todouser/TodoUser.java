package com.todolist.demo.todouser;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TodoUser {
    @Id
    private String userName;
    private String name;
    private String password;

    public TodoUser() {
    }

    public TodoUser(String userName, String name, String password) {
        this.userName = userName;
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TodoUser{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
