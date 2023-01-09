package com.example.alluser.controller;


import com.example.alluser.mapper.SysUserMapper;
import com.example.alluser.mapper.TaskMapper;
import com.example.alluser.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    TaskMapper taskMapper;


    @RequestMapping("/")
    public String adminAllUser(Model model) {
        List<Task> taskes = taskMapper.findAllTask();
        List<Task> tasks = new ArrayList<>();
        for (Task task : taskes) {
            Integer uid = task.getUserId();
            if (sysUserMapper.findUserByUserId(uid).getStatus() == 0)
                tasks.add(task);
        }
        model.addAttribute("tasks", tasks);
        return "adminAllUser";
    }

}
