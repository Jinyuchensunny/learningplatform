package com.example.addcolleage.controller;


import com.example.addcolleage.mapper.ColleageMapper;
import com.example.addcolleage.pojo.Colleage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {


    @Autowired
    ColleageMapper colleageMapper;

    @RequestMapping("/")
    public String adminAddColleage(Model model) {
        Colleage colleage = new Colleage();
        model.addAttribute("colleage", colleage);
        return "adminAddColleage";
    }

    @RequestMapping("/adminAddColleageResult")
    public String adminAddColleageResult(Colleage colleage, Model model) {
        String result = "";
        if (colleage.getLongitude() > 136 || colleage.getLongitude() < 73 || colleage.getLatitude() > 79 || colleage.getLatitude() < 30) {
            result = "经纬度不在中国范围内，新增失败！";
        } else {
            colleageMapper.addColleage(colleage);
            result = "新增成功！";
        }
        model.addAttribute("result", result);
        return "adminAddColleageResult";
    }

}
