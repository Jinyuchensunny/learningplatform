package com.example.allcolleage.controller;


import com.example.allcolleage.mapper.ColleageMapper;
import com.example.allcolleage.pojo.Colleage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    ColleageMapper colleageMapper;

    @RequestMapping("/")
    public String adminAllColleage(Model model) {
        List<Colleage> colleages = colleageMapper.findAllColleage();
        model.addAttribute("colleages", colleages);
        return "adminAllColleage";
    }

}
