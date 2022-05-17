package com.cs307.project.service;

import com.cs307.project.entity.User;

public interface IUserService {
    void reg(User user);
    User login(String username, String pwd);
    void changePwd(String username, String oldPwd, String newPwd);
}
