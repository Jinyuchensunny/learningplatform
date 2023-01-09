package com.example.publishexercise.controller;


import com.example.publishexercise.mapper.EduVideoSourceMapper;
import com.example.publishexercise.mapper.ExerciseMapper;
import com.example.publishexercise.pojo.Edu_videosource;
import com.example.publishexercise.pojo.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {


    @Autowired
    ExerciseMapper exerciseMapper;
    @Autowired
    EduVideoSourceMapper eduVideoSourceMapper;

    @RequestMapping("/")
    public String adminPublishExercise(Model model) {
        Exercise exercise = new Exercise();
        model.addAttribute("exercise", exercise);
        return "adminPublishExercise";
    }

    @RequestMapping("/adminPublishExerciseResult")
    public String adminPublishExerciseResult(Exercise exercise, Model model) {
        String result = "";
        Integer vid = exercise.getVideoId();
        Edu_videosource video = eduVideoSourceMapper.findVideoById(vid);
        if (video == null) result = "输入视频号有误，发布失败！";
        if (result.equals("")) {
            exerciseMapper.addExercise(exercise);
            result = "发布成功！";

        }
        model.addAttribute("result", result);
        return "adminPublishExerciseResult";
    }

}
