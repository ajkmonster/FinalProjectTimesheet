package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    DepartmentRepository departmentRepository;
    @GetMapping ("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("departments",departmentRepository.findAll());
        return "registration";
    }

    @PostMapping ("/register")
    public String processRegistrationPrage(@Valid
                                           @ModelAttribute("user") User user,
                                           BindingResult result,
                                           Model model){
        model.addAttribute("user", user);
        if (result.hasErrors())
        {
            return "registration";
        }
        else
        {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        return "index";
    }


    @RequestMapping("/")
    public String index(Principal principal, Model model){
        User myuser = ((CustomUserDetails)((UsernamePasswordAuthenticationToken) principal)
                .getPrincipal()).getUser();
        model.addAttribute("myuser",myuser);
        return "index";
    }
    @RequestMapping("/login")
    public String login(Model model){

        return "login";
    }

//    @RequestMapping("/admin")
//    public String admin(){
//        return "admin";
//    }
    @RequestMapping("/timesheetentry")
    public String timesheetentry(){
        return "timesheetentry";
    }
    @PostMapping("/mytimesheetentryprocess")
    public String mytimesheetentryprocess(){
        return "mytimesheet";
    }
    @RequestMapping("/mytimesheet")
    public String mytimesheet(){
        return "mytimesheet";
    }
    @RequestMapping("/personalinfo")
    public String personalinfo(Model model){
        model.addAttribute(userService.getUser());
        return "personalinfo";
    }
    //only supervisor sees this
    @RequestMapping("/tsapproval")
    public String tsapproval(){
        return "tsapproval";
    }


}