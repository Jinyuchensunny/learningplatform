package com.example.allusers.controller;


import com.example.allusers.mapper.SysUserMapper;
import com.example.allusers.pojo.Sys_user;
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

    @RequestMapping("/")
    public String adminAllUsers(Model model){
        List<Sys_user> users = sysUserMapper.findAllUser();
        model.addAttribute("users", users);
        return "adminAllUsers";
    }

}
