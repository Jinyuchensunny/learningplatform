package com.learningplatform.controller;

import com.learningplatform.mapper.SysUserMapper;
import com.learningplatform.pojo.Sys_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.UUID;

@Controller
public class RegisterController {
    @Autowired
    SysUserMapper sysUserMapper;

    @RequestMapping("/toRegister")//前往注册页面
    public String toRegister(Model model) {
        model.addAttribute("msg","");
        return "register";
    }

    @RequestMapping("/register")//前往注册页面
    public String register(@RequestParam("LoginName")String loginName,
                           @RequestParam("Password")String password,
                           @RequestParam("UserName")String userName,
                           @RequestParam("Email")String email,
                           @RequestParam("Tel")String tel,
                           Model model) {
        Sys_user sys_user=sysUserMapper.findUserByLoginName(loginName);
        if(sys_user!=null){
            model.addAttribute("msg","该账号名已存在");
            return "register";
        }
        if(tel.length()!=11){
            model.addAttribute("msg","电话号码格式不符合要求");
            return "register";
        }
        else{
            for(int i=0;i<11;i++){
                if(tel.charAt(i)>'9'||tel.charAt(i)<'0'){
                    model.addAttribute("msg","电话号码格式不符合要求");
                    return "register";
                }
            }
        }
        if(password.length()<6){
            model.addAttribute("msg","密码至少六位");
            return "register";
        }
        Sys_user newUser=new Sys_user();
        newUser.setLogin_name(loginName);
        newUser.setLogin_pwd(password);
        newUser.setUser_name(userName);
        newUser.setEmail(email);
        newUser.setTel(tel);

        newUser.setStatus(0);

        Timestamp now = new Timestamp(System.currentTimeMillis());
        newUser.setCreate_time(now);

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");//修改了数据库中uuid字段的长度 20->50
        newUser.setUuid(uuid);

        newUser.setRole_id(0);
        newUser.setBindingRole(0);
        newUser.setIs_examine(0);
        sysUserMapper.addUser(newUser);
        model.addAttribute("msg","");
        return "login";
    }

}
