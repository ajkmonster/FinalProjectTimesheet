package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    UserRepository userRepository;



    @RequestMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, Model model){
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("user", userRepository.findById(id).get());
        return "editinfo";
    }

    @RequestMapping ("/editinfo")
    public String editprofile (Model model){
        model.addAttribute("user",userService.getUser());
        model.addAttribute("departments",departmentRepository.findAll());
        return "editinfo";
    }

    @PostMapping("editprofile")
    public String processForm(@ModelAttribute("user") @Valid User user, BindingResult result, Model model
    ) {
        if (result.hasErrors()){
            model.addAttribute("departments",departmentRepository.findAll());
            return "editinfo";
        }
        userRepository.save(user);
        model.addAttribute("user",user);
        return "redirect:/personalinfo";
    }




}
