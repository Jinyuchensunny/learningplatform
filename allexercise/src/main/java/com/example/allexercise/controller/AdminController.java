package com.example.allexercise.controller;


import com.example.allexercise.mapper.ExerciseMapper;
import com.example.allexercise.pojo.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    ExerciseMapper exerciseMapper;

    @RequestMapping("/")
    public String adminAllExercise(Model model) {
        List<Exercise> exercises = exerciseMapper.findAllExercise();
        model.addAttribute("exercises", exercises);
        return "adminAllExercise";
    }

}
