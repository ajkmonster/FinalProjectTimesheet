package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    TimeSheetRespository timeSheetRespository;
    @Autowired
    UserRepository userRepository;
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
            departmentRepository.findAll();
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


    @RequestMapping("/mytimesheet")
    public String mytimesheet(){
        return "mytimesheet";
    }
    @RequestMapping("/personalinfo")
    public String personalinfo(Model model){
       model.addAttribute("user",userService.getUser());
        return "personalinfo";
    }
    //only supervisor sees this
    @RequestMapping("/tsapproval")
    public String tsapproval(){
        return "tsapproval";
    }

    @GetMapping("/timesheetentry")
    public String timesheetentry(Model model){
    model.addAttribute("timesheet",new TimeSheet());
    return "timesheetentry";
    }
    @PostMapping("/timesheetentryprocess")
    public String timesheetentryprocess(@ModelAttribute("timesheet") @Valid TimeSheet timeSheet , BindingResult result,
                                          Model model) {
        if (result.hasErrors()) {
            return "timesheetentry";
        }
        timeSheet.setUser(userService.getUser());
        timeSheetRespository.save(timeSheet);
        return "mytimesheet";
    }

    @RequestMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("user", userRepository.findById(id).get());
        return "editinfo";
    }

    @PostMapping("/editinfo")
    public String processForm(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {

        if(result.hasErrors()){
            model.addAttribute("departments",departmentRepository.findAll());
            return "editinfo";
        }

        userRepository.save(user);
        model.addAttribute("user",user);
        return "redirect:/personalinfo";
    }


}