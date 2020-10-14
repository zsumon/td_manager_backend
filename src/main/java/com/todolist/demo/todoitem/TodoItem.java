package com.todolist.demo.todoitem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todolist.demo.todouser.TodoUser;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date(System.currentTimeMillis());
    private boolean isDone = false;
    private boolean isPublic = false;

    public TodoItem() {
    }

    public TodoItem(String title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(name = "todoUser_userName")
    private TodoUser todoUser;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TodoUser getTodoUser() {
        return todoUser;
    }

    public void setTodoUser(TodoUser user) {
        this.todoUser = user;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean done) {
        isDone = done;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                ", isDone=" + isDone +
                ", isPublic=" + isPublic +
                ", todoUser=" + todoUser +
                '}';
    }
}
