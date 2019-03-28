package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

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
    @Autowired
    TSTimesRepository tsTimesRepository;
    @RequestMapping ("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("departments",departmentRepository.findAll());
        return "registration";
    }

    @PostMapping ("/register")
    public String processRegistrationPrage(@Valid@ModelAttribute("user") User user,
                                           BindingResult result,
                                           Model model){
        if (result.hasErrors())
        {
            model.addAttribute("departments",departmentRepository.findAll());
            return "registration";
        }
        else
        {
            user.setUsername();
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
    public String mytimesheet(Model model){
        User user = new User();
        user = userService.getUser();
        ArrayList<TimeSheet> results = (ArrayList<TimeSheet>)
                timeSheetRespository.findByUser(user);
        model.addAttribute("timesheets",results);
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
    model.addAttribute("timeSheet",new TimeSheet());
//    model.addAttribute("tstimes", new TSTimes());
    return "timesheetentry";
    }
    @PostMapping("/timesheetentryprocess")
    public String timesheetentryprocess(@ModelAttribute("timeSheet") TimeSheet timeSheet ,
                                        @RequestParam("payCode") String[] paycode,@RequestParam("startTime") String[] starttime, @RequestParam("endTime") String[] endtime,
                                        @RequestParam("date") String[] date, @RequestParam("hours") Double[] hours, Model model) {

//        if (result.hasErrors()) {
//            return "timesheetentry";
//        }
//        DateTimeFormatter df = new DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
//        LocalDate timesheetDate = LocalDate.parse(date, df);
//        timeSheet.setDate(timesheetDate);
        TSTimes[] times = new TSTimes[date.length];
        for (int i=0;i<times.length;i++){
            TSTimes t = new TSTimes(paycode[i],starttime[i],date[i],hours[i],endtime[i]);
            tsTimesRepository.save(t);
            times[i]=t;
        }

        timeSheet.setTsTimes(times);
        timeSheet.setUser(userService.getUser());
        timeSheetRespository.save(timeSheet);


        for(TSTimes t : timeSheet.getTsTimes()){
            t.setTimeSheet(timeSheet);
            tsTimesRepository.save(t);
        }

        ArrayList<TimeSheet> results = (ArrayList<TimeSheet>)
                timeSheetRespository.findByUser(userService.getUser());
        model.addAttribute("timesheets",results);
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