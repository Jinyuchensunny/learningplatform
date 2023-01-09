package com.example.addstudio.controller;


import com.example.addstudio.mapper.ColleageMapper;
import com.example.addstudio.mapper.EduStudioMapper;
import com.example.addstudio.pojo.Colleage;
import com.example.addstudio.pojo.Edu_studio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    ColleageMapper colleageMapper;
    @Autowired
    EduStudioMapper eduStudioMapper;

    @RequestMapping("/")
    public String adminAddStudio(Model model) {
        Edu_studio studio = new Edu_studio();
        model.addAttribute("studio", studio);
        return "adminAddStudio";
    }

    @RequestMapping("/adminAddStudioResult")
    public String adminAddStudioResult(Edu_studio studio, Model model) {
        String result = "";
        Integer cid = studio.getColleageId();
        Colleage colleage = colleageMapper.findColleageByColleageId(cid);
        if (colleage == null) result = "所属学院id有误，新增失败！";
        else {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            studio.setCreate_time(now);
            eduStudioMapper.addStudio(studio);
            result = "新增成功！";
        }
        model.addAttribute("result", result);
        return "adminAddStudioResult";
    }

}
