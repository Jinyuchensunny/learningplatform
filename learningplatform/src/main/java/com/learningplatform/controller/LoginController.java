package com.learningplatform.controller;

import com.learningplatform.mapper.ColleageMapper;
import com.learningplatform.mapper.MessageMapper;
import com.learningplatform.mapper.SysUserMapper;
import com.learningplatform.pojo.Colleage;
import com.learningplatform.pojo.Message;
import com.learningplatform.pojo.Sys_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    ColleageMapper colleageMapper;

    @Autowired
    MessageMapper messageMapper;

    @RequestMapping("/toLogin")//前往登录页面
    public String toLogin(Model model) {
        model.addAttribute("msg", "");
        return "login";
    }

    @RequestMapping("/login")//判断登录是否成功，前往相应页面
    public String login(@RequestParam("LoginName") String loginName, @RequestParam("Password") String password, Model model, HttpServletRequest request) {
        Sys_user sys_user = sysUserMapper.findUserByLoginName(loginName);
        if (sys_user == null || sys_user.getStatus() == 1) {
            model.addAttribute("msg", "账号不存在");
            return "login";
        }
        if (sys_user.getStatus() == 2) {
            model.addAttribute("msg", "该账号被冻结");
            return "login";
        }
        if (sys_user.getLogin_pwd().equals(password)) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            sys_user.setLast_login_time(now);
            String ip = request.getRemoteAddr();//?????
            sys_user.setLast_login_ip(ip);
            sysUserMapper.updateUserByLastLoginTime(sys_user);
            if (sys_user.getRole_id() == 1) return "adminIndex";//管理员登录

            List<Colleage> colleages = colleageMapper.findAllColleage();

            Colleage colleage = colleages.get(0);
            model.addAttribute("colleages",/* JSON.toJSONString*/(colleages));
            model.addAttribute("userId", sys_user.getUser_id());
            return "china";
        } else {
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }

    @RequestMapping("/questions")//Q&A
    public String questions(Model model) {
        return "Q&A";
    }

    @RequestMapping("/introduction")//introduction
    public String introduction(Model model) {
        Message message = new Message();
        model.addAttribute("message", message);
        return "introduction";
    }

    @RequestMapping("/addMessageFinish")//introduction
    public String introductionResult(Message message, Model model) {
        messageMapper.addMessage(message);
        model.addAttribute("msg", "");
        return "login";
    }


}
