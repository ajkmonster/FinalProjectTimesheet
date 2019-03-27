package com.example.demo;

import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.sql.Time;

@Entity
public class TimeSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String payCode;

    private String startTime;

    private String endTime;
    @ManyToOne
    private User user;

    public TimeSheet(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
