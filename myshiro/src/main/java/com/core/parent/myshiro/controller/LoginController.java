package com.core.parent.myshiro.controller;

import com.core.parent.myshiro.entity.Role;
import com.core.parent.myshiro.entity.User;
import com.core.parent.myshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService loginService;

    //退出的时候是get请求，主要是用于退出
    @RequestMapping(value = "/loginPage")
    public ModelAndView loginPage() {
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    //post登录
    @RequestMapping(value = "/login")
    public String login(String username,String password,boolean rememberMe) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                username,password,rememberMe);
        //进行验证，这里可以捕获异常，然后返回对应信息
        subject.login(usernamePasswordToken);
        return "index";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    //登出
    @RequestMapping(value = "/logout")
    public ModelAndView logout() {
        return new ModelAndView("logout");
    }

    //错误页面展示
    @RequestMapping(value = "/error", method = RequestMethod.POST)
    public String error() {
        return "error ok!";
    }

   /* //数据初始化
    @RequestMapping(value = "/addUser")
    public String addUser(@RequestBody Map<String, Object> map) {
        User user = loginService.addUser(map);
        return "addUser is ok! \n" + user;
    }

    //角色初始化
    @RequestMapping(value = "/addRole")
    public String addRole(@RequestBody Map<String, Object> map) {
        Role role = loginService.addRole(map);
        return "addRole is ok! \n" + role;
    }*/

    //注解的使用
    @RequiresRoles("admin")
    @RequiresPermissions("create")
    @RequestMapping(value = "/create")
    public String create() {
        return "Create success!";
    }

    @RequestMapping("/tag")
    public ModelAndView shiroTagTest(){
        ModelAndView mv = new ModelAndView("shiro_tag_demo");
        return  mv;
    }
}
