package com.app.calendar.web;

import com.app.calendar.task.Task;
import com.app.calendar.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute(user);
        return "registration";
    }

    @GetMapping("/calendar")
    public String calendar(Model model) {
        Task task = new Task();
        model.addAttribute(task);
        return "calendar";
    }

    @GetMapping("/displayTaskByClickExperiment")
    public String displayTaskByClickExperimentPage(Model model) {
        System.out.println("experiment page returned");
        Task task = new Task();
        model.addAttribute(task);
        return "displayTaskByClickExperiment";
    }

    @GetMapping("/ajax")
    public String ajax() {return "ajax";}

//    @GetMapping("/displayTasks")
//    public String displayTasks() {
//        return "displayTasks";
//    }
}