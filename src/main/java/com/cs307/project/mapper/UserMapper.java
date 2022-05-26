package com.cs307.project.mapper;

import com.cs307.project.entity.User;

import java.util.List;

public interface UserMapper {
    void insertUser(User user);

    User selectByName(String username);

    void updatePwd(String username, String newPwd);

    List<User> selectUser();

    void grantInsert(String username);

    void grantDelete(String username);

    void grantUpdate(String username);

    void grantSelect(String username);

    void revokeInsert(String username);

    void revokeDelete(String username);

    void revokeUpdate(String username);

    void revokeSelect(String username);
}
