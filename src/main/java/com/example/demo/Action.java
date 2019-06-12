package com.example.demo;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Entity
public class Action {
    private String action;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private User userAction;

    private LocalDate currenttime;

    public Action(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(LocalDate currenttime) {
        this.currenttime = currenttime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public User getUserAction() {
        return userAction;
    }

    public void setUserAction(User userAction) {
        this.userAction = userAction;
    }
}
