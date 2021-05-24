package com.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author WuRui
 * @ClassName MyController
 * @Date 2021/5/24 10:19
 * @Version 1.0
 * @Description //TODO
 **/
@Controller
public class MyController {

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("msg","首页");
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin( ){
        return "login";
    }

    @RequestMapping("/user/add")
    public String add(Model model){
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update(Model model){
        return "user/update";
    }

    @RequestMapping("/login")
    public String login(String username,String password,Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            // 登录操作
            subject.login(token);
            return "index";
        } catch (UnknownAccountException uae) {
            model.addAttribute("msg","用户名错误");
            return "login";
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }
}
