package com.example.allbook.controller;


import com.example.allbook.mapper.EduBooksourceMapper;
import com.example.allbook.pojo.Edu_booksource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    EduBooksourceMapper eduBooksourceMapper;

    @RequestMapping("/")
    public String adminAllBook(Model model) {
        List<Edu_booksource> books = eduBooksourceMapper.findAllBook();
        model.addAttribute("books", books);
        return "adminAllBook";
    }

}
