package com.example.publishtask.controller;


import com.example.publishtask.mapper.EduBooksourceMapper;
import com.example.publishtask.mapper.EduVideoSourceMapper;
import com.example.publishtask.mapper.SysUserMapper;
import com.example.publishtask.mapper.TaskMapper;
import com.example.publishtask.pojo.Edu_booksource;
import com.example.publishtask.pojo.Edu_videosource;
import com.example.publishtask.pojo.Sys_user;
import com.example.publishtask.pojo.Task;
import com.example.publishtask.util.SubmitTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    EduVideoSourceMapper eduVideoSourceMapper;
    @Autowired
    EduBooksourceMapper eduBooksourceMapper;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    TaskMapper taskMapper;

    @RequestMapping("/")
    public String adminPublishTask(Model model) {
        SubmitTask submitTask = new SubmitTask();
        model.addAttribute("submitTask", submitTask);
        return "adminPublishTask";
    }

    @RequestMapping("/adminPublishTaskResult")
    public String adminPublishTaskResult(SubmitTask submitTask, Model model) {

        String result = "";
        if (submitTask.getType() == 1) {
            Edu_videosource video = eduVideoSourceMapper.findVideoById(submitTask.getVbid());
            if (video == null) result = "视频号有误，发布失败！";
        } else if (submitTask.getType() == 2) {
            Edu_booksource book = eduBooksourceMapper.findBookById(submitTask.getVbid());
            if (book == null) result = "书籍号有误，发布失败！";
        }

        List<Integer> usersId = new ArrayList<>();
        Integer num = -1;
        String ids = submitTask.getUsersId();
        for (int i = 0; i < ids.length(); i++) {
            char c = ids.charAt(i);
            if (c == ' ') {
                if (num != -1) {
                    usersId.add(num);
                    num = -1;
                }
            } else if (c - '0' >= 0 && c - '0' <= 9) {
                if (num == -1) num = c - '0';
                else num = num * 10 + (c - '0');
            } else result = "输入的用户id不合规范，发布失败！";
        }
        if (num != -1) usersId.add(num);

        if (result.equals("")) {
            for (int i = 0; i < usersId.size(); i++) {
                Integer id = usersId.get(i);
                Sys_user user = sysUserMapper.findUserByUserId(id);
                if (user == null) result = "用户id有误，发布失败！";
            }
        }

        if (result.equals("")) {

            for (int i = 0; i < usersId.size(); i++) {
                Task task = new Task();
                task.setStatus(0);
                task.setDetail(submitTask.getDetail());
                task.setType(submitTask.getType());
                task.setLevel(submitTask.getLevel());
                if (submitTask.getType() == 1) {
                    task.setVideoId(submitTask.getVbid());
                } else {
                    task.setBookId(submitTask.getVbid());
                }
                Timestamp now = new Timestamp(System.currentTimeMillis());
                task.setCreateTime(now);
                task.setUserId(usersId.get(i));
                //System.out.println(task);
                taskMapper.addTask(task);
            }
            result = "发布成功!";

        }

        model.addAttribute("result", result);
        return "adminPublishTaskResult";
    }


}
