package com.example.demo;

import org.springframework.stereotype.Controller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Scanner;

@Entity
public class TimeSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String payCode;

    private String startTime;

    private String endTime;

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

    public void timeCalculate(){

        Scanner keyboard = new Scanner(System.in);

        int hoursWorked = keyboard.nextInt();

        double payRate = 45;
        int otHours = hoursWorked - 40;


        //employees work more than 40hrs
        if (hoursWorked > 40){
            double otPay = (otHours) * (payRate * 1.5);
            double wages = (otPay) + (40 * payRate);

            //employees who work no overtime
        } else if (hoursWorked <= 40){
            double wkPay = (hoursWorked) * (payRate);
        }

        //calculation for sick leave
        double sickLeave = 0;
        double totalHours = (hoursWorked + otHours);

        if (totalHours >= 100)
        {sickLeave = (sickLeave +2.5);}

        if (totalHours >= 300)
        {sickLeave = (sickLeave +4.5);}

        if (totalHours >= 500)
        {sickLeave = (sickLeave +5);}

    }
}

