package com.example.demo;

public class Email {

    private String reasonText;

    public Email(String reasonText) {
        this.reasonText = reasonText;
    }

    public Email() {
    }

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }
}