package com.example.updatestuinfo.controller;


import com.example.updatestuinfo.mapper.SysUserMapper;
import com.example.updatestuinfo.mapper.TaskMapper;
import com.example.updatestuinfo.pojo.Sys_user;
import com.example.updatestuinfo.pojo.Task;
import com.example.updatestuinfo.util.UpdateStu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {


    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    TaskMapper taskMapper;

    @RequestMapping("/")
    public String adminUpdateStuInfo(Model model) {
        UpdateStu updateStu = new UpdateStu();
        model.addAttribute("updateStu", updateStu);
        return "adminUpdateStuInfo";
    }

    @RequestMapping("/adminUpdateStuInfoResult")
    public String adminUpdateStuInfoResult(
            UpdateStu updateStu, Model model) {
        String result = "";
        Integer updateStuUid = updateStu.getUserId();
        Integer updateStuTid = updateStu.getTaskId();
        Integer test=0;
        if(updateStuTid!=null)
            test=taskMapper.findTaskById(updateStuTid).getUserId();
        if (updateStuUid == null || sysUserMapper.findUserByUserId(updateStuUid).getStatus() != 0) {
            result = "该学员不存在！！！";
        } else if (updateStuTid != null && taskMapper.findTaskById(updateStuTid) == null) {
            result = "该任务不存在";
        } else if (updateStuTid != null && !updateStuUid.equals(test)) {
            result = "该学员没有此任务,请重新确认填写信息是否正确";
        } else {
            String loginName = updateStu.getLogin_name();
            String userName = updateStu.getUser_name();
            String user_pwd = updateStu.getUser_pwd();
            Integer task_status = updateStu.getTask_status();
            String email = updateStu.getEmail();
            String tel = updateStu.getTel();

            Sys_user sys_user = new Sys_user();
            Sys_user sys_user1 = sysUserMapper.findUserByUserId(updateStuUid);


            sys_user.setUser_id(updateStuUid);
            if (email != "")
                sys_user.setEmail(email);
            else {
                sys_user.setEmail(sys_user1.getEmail());
            }

            if (loginName != "")
                sys_user.setLogin_name(loginName);
            else
                sys_user.setLogin_name(sys_user1.getLogin_name());

            if (user_pwd != "")
                sys_user.setLogin_pwd(user_pwd);
            else
                sys_user.setLogin_pwd(sys_user1.getLogin_pwd());

            if (userName != "")
                sys_user.setUser_name(userName);
            else
                sys_user.setUser_name(sys_user1.getUser_name());

            if (tel != "")
                sys_user.setTel(tel);
            else
                sys_user.setTel(sys_user1.getTel());

            sysUserMapper.updateUser(sys_user);
            result = "更新完成";

            if (updateStuTid != null) {
                Task task = new Task();
                task.setTaskId(updateStuTid);
                if (task_status != null) {
                    task.setStatus(task_status);
                } else {
                    task.setStatus(taskMapper.findTaskById(updateStuTid).getStatus());
                }
                taskMapper.updateTask(task);
            } else if (task_status != null) {
                result = "请输入任务id";
            }
        }
        model.addAttribute("result", result);
        return "adminUpdateStuInfoResult";
    }

}
