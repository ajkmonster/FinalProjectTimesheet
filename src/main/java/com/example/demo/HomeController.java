package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ReportAsSingleViolation;
import javax.validation.Valid;
import java.security.Principal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    EmailService emailService;

    @Autowired
    ActionRepository actionRepository;

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
                                           Model model, Action action){
        if (result.hasErrors())
        {
            model.addAttribute("departments",departmentRepository.findAll());
            return "registration";
        }
        else
        {
            user.setUsername();
            userService.saveUser(user);
            action.setUserAction(user);
            action.setAction("Created your account");
            action.setCurrenttime(LocalDate.now());
            actionRepository.save(action);
            model.addAttribute("message", "User Account Created");
        }
        return "redirect:/login";
    }


    @RequestMapping("/")
    public String index(Principal principal, Model model){

        User myuser = ((CustomUserDetails)((UsernamePasswordAuthenticationToken) principal)
                .getPrincipal()).getUser();
        model.addAttribute("myuser",myuser);
        return "index";
    }
    @GetMapping("/login")
    public String login(){

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
    public String timesheetentry(Model model,Action action){
    model.addAttribute("timeSheet",new TimeSheet());
        action.setUserAction(userService.getUser());
        action.setAction("Created a Timesheet");
        action.setCurrenttime(LocalDate.now());
        actionRepository.save(action);
//    model.addAttribute("tstimes", new TSTimes());
    return "timesheetentry";
    }
    @PostMapping("/timesheetentryprocess")
    public String timesheetentryprocess(@ModelAttribute("timeSheet") TimeSheet timeSheet ,
                                        @RequestParam("payCode") String[] paycode,@RequestParam("startTime") String[] starttime, @RequestParam("endTime") String[] endtime,
                                        @RequestParam("date") String[] date, @RequestParam("hours") Double[] hours, Model model,Action action, @RequestParam("page") int page) {

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
        timeSheet.setStatus(0);
        timeSheet.setUser(userService.getUser());
        timeSheetRespository.save(timeSheet);


        for(TSTimes t : timeSheet.getTsTimes()){
            t.setTimeSheet(timeSheet);
            tsTimesRepository.save(t);
        }
        if(page==1) {
            action.setUserAction(userService.getUser());
            action.setAction("Submitted Time sheet " + timeSheet.getId() + " for approval");
            action.setCurrenttime(LocalDate.now());
            actionRepository.save(action);
        }
        if(page==2) {
            action.setUserAction(userService.getUser());
            action.setAction("Updated Time sheet " + timeSheet.getId() + " for approval");
            action.setCurrenttime(LocalDate.now());
            actionRepository.save(action);
        }

        ArrayList<TimeSheet> results = (ArrayList<TimeSheet>)
                timeSheetRespository.findByUser(userService.getUser());
        model.addAttribute("timesheets",results);
        return "mytimesheet";
    }

    @RequestMapping("/detailtimesheet/{id}")
    public String updateTimesheet(@PathVariable("id") long id, Model model, Action action){
        model.addAttribute("timesheet", timeSheetRespository.findById(id).get());
        model.addAttribute("userCurrent",userService.getUser());
        action.setUserAction(userService.getUser());
        action.setAction("Looked at detail of Timsheet "+ id);
        action.setCurrenttime(LocalDate.now());
        actionRepository.save(action);
        return "timesheetdetail";
    }

    @RequestMapping("/updatetimesheet/{id}")
    public String editTimesheet(@PathVariable("id") long id, Model model){
        model.addAttribute("timeSheet", timeSheetRespository.findById(id).get());
        return "timesheetedit";
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
    @RequestMapping("/tslist")
    public String pullInformation(Model model,Action action)
    {
        model.addAttribute("supervisor",userService.getUser());
        User supervisor = userService.getUser();
        ArrayList<User> results = (ArrayList<User>)
               userRepository.findByDepartment(supervisor.department);
        model.addAttribute("users",results);
        int x =0;
        ArrayList<TimeSheet> resultstimesheet = new ArrayList<TimeSheet>();
        for(User user : results){
            Set<TimeSheet> timesheets =user.getTimeSheet();
            for(TimeSheet t : timesheets)
            resultstimesheet.add(x,t);
            x += x;
        }
        action.setUserAction(userService.getUser());
        action.setAction("Pulled Approved, Pending, and Rejected Time Sheets");
        action.setCurrenttime(LocalDate.now());
        actionRepository.save(action);
        model.addAttribute("timesheets",resultstimesheet);
        return "tslist";
    }

    @PostMapping("/approve")
    public String timesheetentryapprove(@ModelAttribute("timesheet") TimeSheet timeSheet ,
                                        @RequestParam("payCode") String[] paycode,@RequestParam("startTime") String[] starttime, @RequestParam("endTime") String[] endtime,
                                        @RequestParam("date") String[] date, @RequestParam("hours") Double[] hours, @RequestParam("id") long id, Model model,Action action) {

        TSTimes[] times = new TSTimes[date.length];
        for (int i=0;i<times.length;i++){
            TSTimes t = new TSTimes(paycode[i],starttime[i],date[i],hours[i],endtime[i]);
            tsTimesRepository.save(t);
            times[i]=t;
        }

        timeSheet.setTsTimes(times);
        timeSheet.setStatus(1);
        timeSheet.setUser(timeSheetRespository.findById(id).get().getUser());
        timeSheetRespository.save(timeSheet);


        for(TSTimes t : timeSheet.getTsTimes()){
            t.setTimeSheet(timeSheet);
            tsTimesRepository.save(t);
        }
        action.setUserAction(userService.getUser());
        action.setAction("Approved Time Sheet " + timeSheet.getId() + " of " + timeSheet.getUser().getFirstName()
                + " " + timeSheet.getUser().getLastName());
        action.setCurrenttime(LocalDate.now());
        actionRepository.save(action);
        return "redirect:/tslist";
    }
    @PostMapping("/reject")
    public String timesheetentryreject(@ModelAttribute("timeSheet") TimeSheet timeSheet ,
                                        @RequestParam("payCode") String[] paycode,@RequestParam("startTime") String[] starttime, @RequestParam("endTime") String[] endtime,@RequestParam("id") long id,
                                        @RequestParam("date") String[] date, @RequestParam("hours") Double[] hours, Model model, @RequestParam("reasonText") String reasonText, Action action) {

        TSTimes[] times = new TSTimes[date.length];
        for (int i=0;i<times.length;i++){
            TSTimes t = new TSTimes(paycode[i],starttime[i],date[i],hours[i],endtime[i]);
            tsTimesRepository.save(t);
            times[i]=t;
        }

        timeSheet.setTsTimes(times);
        timeSheet.setStatus(2);
        timeSheet.setUser(timeSheetRespository.findById(id).get().getUser());
        timeSheetRespository.save(timeSheet);


        for(TSTimes t : timeSheet.getTsTimes()){
            t.setTimeSheet(timeSheet);
            tsTimesRepository.save(t);
        }
        action.setUserAction(userService.getUser());
        action.setAction("Rejected Time Sheet " + timeSheet.getId() + " of " + timeSheet.getUser().getFirstName()
                + " " + timeSheet.getUser().getLastName());
        action.setCurrenttime(LocalDate.now());
        actionRepository.save(action);
        Email email = new Email();
        email.setReasonText(reasonText);
        emailService.SendSimpleEmail(email);
        return "redirect:/tslist";
    }
    @RequestMapping("/paystub")
    public String paystub(Model model, Action action){
        double totalhours = 0;
        ArrayList<TimeSheet> timeSheets =(ArrayList<TimeSheet>)timeSheetRespository.findByUser(userService.getUser());
        for (TimeSheet t : timeSheets){
            if(t.getStatus()== 1){
                for (TSTimes x : t.getTsTimes()){
                    totalhours= totalhours + x.getHoursWorked();
                }
                t.setTotalhours(totalhours);
                timeSheetRespository.save(t);
            }
        }
        action.setUserAction(userService.getUser());
        action.setAction("Retrieving Paystub for approved Time Sheets");
        action.setCurrenttime(LocalDate.now());
        actionRepository.save(action);
        model.addAttribute("user",userService.getUser());
        model.addAttribute("timesheets", timeSheets);
        return "paystub";
    }
    @RequestMapping("/action")
    public String actionpage(Model model){
        ArrayList<Action> actions =(ArrayList<Action>)actionRepository.findByUserAction(userService.getUser());
        model.addAttribute("actions", actions);
        return "action";
    }

}