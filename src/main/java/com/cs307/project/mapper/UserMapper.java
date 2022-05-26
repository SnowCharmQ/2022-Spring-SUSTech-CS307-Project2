package com.cs307.project.mapper;

import com.cs307.project.entity.User;

import java.util.List;

public interface UserMapper {
    void insertUser(User user);

    User selectByName(String username);

    void updatePwd(String username, String newPwd);

    List<User> selectUser();
}
