package com.cs307.project.controller;

import com.cs307.project.entity.User;
import com.cs307.project.service.IUserService;
import com.cs307.project.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {
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
    public JsonResult<Void> changePwd(@RequestBody String pwds, HttpSession session) {
        String[] strings = pwds.split("&");
        String oldPwd = strings[0].substring(12);
        String newPwd = strings[1].substring(12);
        String username = getUsernameFromSession(session);
        userService.changePwd(username, oldPwd, newPwd);
        return new JsonResult<>(ok);
    }

    @RequestMapping("db-users")
    public JsonResult<List<User>> select() {
        List<User> list = userService.select();
        return new JsonResult<>(ok, list);
    }

    @RequestMapping(value = "db-user-management", produces = "application/json;charset=utf-8")
    public JsonResult<Void> userManage(@RequestBody String data) {
        userService.manage(data);
        return new JsonResult<>(ok);
    }

    @RequestMapping("db-info")
    public JsonResult<User> getUserInfo(HttpSession session) {
        String username = getUsernameFromSession(session);
        User user = userService.selectUser(username);
        return new JsonResult<>(ok, user);
    }
}
