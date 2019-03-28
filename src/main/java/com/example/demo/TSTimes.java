package com.example.demo;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;
import java.sql.Time;

@Entity
public class TSTimes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String payCode;

    private String startTime;

    private String date;

    private double hoursWorked;

    private String endTime;

    @ManyToOne
    private TimeSheet timeSheet;

    public TSTimes(){

    }

    public TSTimes(String payCode, String startTime, String date, double hoursWorked, String endTime) {
        this.payCode = payCode;
        this.startTime = startTime;
        this.date = date;
        this.hoursWorked = hoursWorked;
        this.endTime = endTime;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public TimeSheet getTimeSheet() {
        return timeSheet;
    }

    public void setTimeSheet(TimeSheet timeSheet) {
        this.timeSheet = timeSheet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void timeCalculate(){

//        Scanner keyboard = new Scanner(System.in);
//
//        int hoursWorked = keyboard.nextInt();





        //employees work more than 40hrs
        if (hoursWorked > 40){
            double otHours = hoursWorked - 40;
            double otPay = (otHours) * (45 * 1.5);
            double wages = (otPay) + (40 * 45);

            //employees who work no overtime
        } else if (hoursWorked <= 40){
            double wkPay = (hoursWorked) * (45);
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
