package com.example.delstudent.controller;


import com.example.delstudent.mapper.SysUserMapper;
import com.example.delstudent.util.DelStu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    SysUserMapper sysUserMapper;

    @RequestMapping("/")
    public String adminPermission(Model model) {
        DelStu student = new DelStu();
        model.addAttribute("student", student);
        return "adminDelStudent";
    }

    @RequestMapping("/adminDelStudentResult")
    public String adminPermissionResult(DelStu delStu, Model model) {
        String result = "";
        Integer delStuId = delStu.getUser_id();
        if (sysUserMapper.findUserByUserId(delStuId) == null ) {
            result = "不存在该学员信息，请重新输入！！！";
        } else {
            sysUserMapper.DeleteUserByUserId(delStu);
            if (delStu.getStatus() == 1)
                result = "成功删除该用户";
            else if(delStu.getStatus() == 2)
                result = "成功冻结该用户";
            else if(delStu.getStatus() == 0)
                result = "已还原该账号";
        }
        model.addAttribute("result", result);
        return "adminDelStudentResult";
    }


}
