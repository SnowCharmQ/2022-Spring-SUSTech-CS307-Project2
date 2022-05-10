package com.cs307.project.mapper;

import com.cs307.project.entity.User;

public interface UserMapper {
    void insertUser(User user);
    User selectByName(String username);
}
