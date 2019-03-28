package com.example.demo;

import org.springframework.stereotype.Controller;


import javax.persistence.*;

import java.sql.Time;

import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;


@Entity
public class TimeSheet {

    private double payRate = 45;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String payCode;

    private String startTime;

    private String date;

    private double hoursWorked;

    private String endTime;
    @ManyToOne
    private User user;



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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void timeCalculate(){

//        Scanner keyboard = new Scanner(System.in);
//
//        int hoursWorked = keyboard.nextInt();




        //employees work more than 40hrs
        if (hoursWorked > 40){
            double otHours = hoursWorked - 40;
            double otPay = (otHours) * (payRate * 1.5);
            double wages = (otPay) + (40 * payRate);

            //employees who work no overtime
        } else if (hoursWorked <= 40){
            double wkPay = (hoursWorked) * (payRate);
        }

        //calculation for sick leave
        double sickLeave = 0;
        double totalHours = (hoursWorked);

        if (totalHours >= 100)
        {sickLeave = (sickLeave +2.5);}

        if (totalHours >= 300)
        {sickLeave = (sickLeave +4.5);}

        if (totalHours >= 500)
        {sickLeave = (sickLeave +5);}

    }

}

