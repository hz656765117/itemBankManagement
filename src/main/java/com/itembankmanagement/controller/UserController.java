package com.itembankmanagement.controller;

import com.itembankmanagement.service.UserService;
import com.itembankmanagement.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    UserService userService;

    //管理员添加用户
    @PostMapping("/add")
    Result userAdd(String name, String pw, String phone) {
        return userService.add(name, pw, phone);
    }

    //管理员删除用户
    @PostMapping("/delete")
    Result delete(Long id) {
        return userService.delete(id);
    }

    //管理员得到某个用户信息
    @GetMapping("/get")
    Result getInfo(Long id){
        return userService.getInfo(id);
    }

    //用户登录
    @PostMapping("/login")
    Result userLogin(HttpServletResponse response, Long id, String pw) {
        return userService.login(response, id, pw);
    }

    //用户登出
    @PostMapping("/logout")
    //@RequiresAuthentication
    Result userLogout(HttpServletResponse response) {
        return userService.logout(response);
    }

    //用户的到自己的信息
    @GetMapping("/info")
    //@RequiresAuthentication
    Result userGetIn(){
        return userService.getInfo();
    }

    //管理员修改用用户信息
    @PostMapping("/modification")
    Result usermoifcation(Long id,String name,String phone,String pw){
        return userService.modification(id,name,phone,pw);
    }

    //用户修改自己的信息
    @PostMapping("/modif")
    //@RequiresAuthentication
    Result userModif( String name,String phone){
        return userService.modif(name,phone);
    }

    //用户修改自己的密码
    @PostMapping("/modifPw")
    //@RequiresAuthentication
    Result userModifPassWord(String oldPw,String newPw){
        return userService.modifPw(oldPw,newPw);
    }

    @GetMapping("/getAll")
    Result userGetAll(){
        return userService.getAll();
    }

}