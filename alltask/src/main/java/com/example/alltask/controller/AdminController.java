package com.example.alltask.controller;


import com.example.alltask.mapper.SysUserMapper;
import com.example.alltask.mapper.TaskMapper;
import com.example.alltask.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    TaskMapper taskMapper;
    @Autowired
    SysUserMapper sysUserMapper;

    @RequestMapping("/")
    public String adminAllTask(Model model) {
        List<Task> taskes = taskMapper.findAllTask();
        List<Task> tasks = new ArrayList<>();
        for (Task t : taskes) {
            Integer uid = t.getUserId();
            if (sysUserMapper.findUserByUserId(uid).getStatus() == 0)
                tasks.add(t);
        }
        model.addAttribute("tasks", tasks);
        return "adminAllTask";
    }

}
