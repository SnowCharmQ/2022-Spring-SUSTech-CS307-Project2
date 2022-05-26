package com.cs307.project.controller;

import com.cs307.project.entity.User;
import com.cs307.project.service.IUserService;
import com.cs307.project.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;

    @RequestMapping("db-reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(ok);
    }

    @RequestMapping("db-login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User data = userService.login(username, password);
        session.setAttribute("username", data.getUsername());
        return new JsonResult<>(ok, data);
    }

    @RequestMapping("db-pwd-change")
    public JsonResult<Void> changePwd(String oldPwd, String newPwd, HttpSession session){
        String username = getUsernameFromSession(session);
        userService.changePwd(username, oldPwd, newPwd);
        return new JsonResult<>(ok);
    }

    @RequestMapping("db-users")
    public JsonResult<List<User>> select(){
        List<User> list = userService.select();
        return new JsonResult<>(ok, list);
    }
}
