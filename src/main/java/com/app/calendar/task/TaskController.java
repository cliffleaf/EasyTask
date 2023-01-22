package com.app.calendar.task;

import com.app.calendar.web.dto.ServiceResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping()
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/calendar/{date}")
    public ResponseEntity<Object> displayTasksByClick(@PathVariable("date") int date) {
        long userId = this.getCurrentUserId();
        List<Task> taskList = taskService.findByDateAndUserId(date, userId);

        // AJAX cannot recognise boolean, but isComplete is needed to set task background
        // so below is to create a list of int. 1 for complete, 0 for incomplete
        ArrayList<Integer> isCompleteList = new ArrayList<Integer>();
        for (Task t : taskList) {
            if (t.isComplete())
                isCompleteList.add(1);
            else
                isCompleteList.add(0);
        }
        ServiceResponseDto<List<Task>> list = new ServiceResponseDto<>("success", taskList, isCompleteList);
        return new ResponseEntity<Object>(list, HttpStatus.OK);
    }

    @GetMapping("/calendar/displayDetail/{taskId}")
    public ResponseEntity<Object> displayDetail(@PathVariable("taskId") long id) {
        Task task = taskService.getTask(id);
        ServiceResponseDto<Task> t = new ServiceResponseDto<>("success", task, null);
        return new ResponseEntity<Object>(t, HttpStatus.OK);
    }

    @PostMapping("/calendar/addTask")
    public String save(HttpServletRequest req, HttpServletResponse res) {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String type = req.getParameter("type");

        System.out.println(name + description + type);

        String dateWithSpace = req.getParameter("date");
        int date = processDateString(dateWithSpace);

        long userId = this.getCurrentUserId();
        Task task;

        if (type.equals("assessment")) {
            task = new Task(name, date, description, false, userId, 1);
            taskService.save(task);
        }
        else {
            int[] dayInterval = new int[] {0, 1, 2, 4, 7, 15};
            for (int i : dayInterval) {
                task = new Task(name, date+i, description, false, userId, 0);
                taskService.save(task);
            }
        }

        return "redirect:/calendar";
        // hides form popup, and goes back to calendar main page
    }

    @RequestMapping("/calendar/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") long taskId) {
        Task task = taskService.getTask(taskId);
        if (task.getType() == 0)
            taskService.deleteTasksByName(task.getName());
        else
            taskService.deleteTask(taskId);
        return "redirect:/calendar";
    }

    @RequestMapping("/calendar/complete/{taskId}/{isComplete}")
    public String completeTask(@PathVariable("taskId") long taskId, @PathVariable("isComplete") boolean isComplete) {
        taskService.updateIsComplete(taskId, !isComplete);
        return "redirect:/calendar";
    }

    @PostMapping("/calendar/edit/{taskId}")
    public String editTask(@PathVariable("taskId") long taskId, HttpServletRequest req) {
        String newName = req.getParameter("name");
        String description = req.getParameter("description");
//        String type = req.getParameter("type");

        Task task = taskService.getTask(taskId);
        String oldName = task.getName();
        task.setName(newName);
        task.setDescription(description);

        if (task.getType() == 1) {
//            task.setType(1);
            taskService.save(task);
        }
        else {
            for (Task t : taskService.findTasksByName(oldName)) {
//                t.setType(0);
                t.setName(newName);
                t.setDescription(description);
                taskService.save(task);
            }
        }
        return "redirect:/calendar";
    }

    private int processDateString(String inputDate) {
        // input is in format of 2 3 2022: for 2nd March 2022
        String[] splitted = inputDate.split(" ");
        String day = splitted[0].length() == 1 ? "0"+splitted[0] : splitted[0];
        String month = splitted[1].length() == 1 ? "0"+splitted[1] : splitted[1];

        // processed to be 20220302
        return Integer.parseInt(splitted[2]+month+day);
    }

    private long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        long userId = taskService.findUserId(username);

        return userId;
    }
}
