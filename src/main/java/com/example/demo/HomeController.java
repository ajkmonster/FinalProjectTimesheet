package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    EmailService emailService;


    @RequestMapping("/")
    public String GetIndex(){
        return "index";
    }

    @PostMapping("/sendSimpleEmail")
    public String SendSimpleEmail(){
        emailService.SendSimpleEmail();
        return "success";
    }
}
