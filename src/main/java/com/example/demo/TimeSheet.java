package com.example.demo;

import org.springframework.stereotype.Controller;


import javax.persistence.*;

import java.sql.Time;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;


@Entity
public class TimeSheet {

    private double payRate = 45;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "timeSheet", cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER)
    public TSTimes[] tsTimes;

    @ManyToOne
    private User user;

    private int status;


    public TimeSheet(){

    }

    public double getPayRate() {
        return payRate;
    }

    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TSTimes[] getTsTimes() {
        return tsTimes;
    }

    public void setTsTimes(TSTimes[] tsTimes) {
        this.tsTimes = tsTimes;
    }

    //    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

