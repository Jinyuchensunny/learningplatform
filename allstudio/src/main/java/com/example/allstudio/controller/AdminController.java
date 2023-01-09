package com.example.allstudio.controller;


import com.example.allstudio.mapper.EduStudioMapper;
import com.example.allstudio.pojo.Edu_studio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    EduStudioMapper eduStudioMapper;

    @RequestMapping("/")
    public String adminAllStudio(Model model) {
        List<Edu_studio> studios = eduStudioMapper.findAllStudio();
        model.addAttribute("studios", studios);
        return "adminAllStudio";
    }

}
