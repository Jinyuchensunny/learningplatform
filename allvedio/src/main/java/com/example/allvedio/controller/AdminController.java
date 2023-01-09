package com.example.allvedio.controller;


import com.example.allvedio.mapper.EduVideoSourceMapper;
import com.example.allvedio.pojo.Edu_videosource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    EduVideoSourceMapper eduVideoSourceMapper;

    @RequestMapping("/")
    public String adminAllVedio(Model model) {
        List<Edu_videosource> videos = eduVideoSourceMapper.findAllVideo();
        model.addAttribute("videos", videos);
        return "adminAllVedio";
    }


}
