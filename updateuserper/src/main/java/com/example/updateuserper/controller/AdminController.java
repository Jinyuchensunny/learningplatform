package com.example.updateuserper.controller;


import com.example.updateuserper.mapper.SysUserMapper;
import com.example.updateuserper.util.Permission;
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
    public String adminUpdateUserPer(Model model) {
        Permission permission = new Permission();
        model.addAttribute("permission", permission);
        return "adminUpdateUserPer";
    }

    @RequestMapping("/adminUpdateUserPerResult")
    public String adminUpdateUserPerResult(Permission permission, Model model) {
        String result = "";
        if (sysUserMapper.findUserByUserId(permission.getUser_id()) == null)
            result = "用户不存在，请重新输入";
        else {
            if (sysUserMapper.findUserByUserId(permission.getUser_id()).getStatus() == 1)
                result = "用户已经被删除，请重新输入";
            else if (sysUserMapper.findUserByUserId(permission.getUser_id()).getStatus() == 2)
                result = "该用户被冻结，请重新输入";
            else {
                sysUserMapper.updateUserByRoleId(permission);
                result = "已经修改该用户的权限";
            }
        }
        model.addAttribute("result", result);
        return "adminUpdateUserPerResult";
    }


}
