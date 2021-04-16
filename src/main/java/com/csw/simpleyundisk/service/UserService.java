package com.csw.simpleyundisk.service;

import com.csw.simpleyundisk.entity.User;

public interface UserService {
    User queryUserByPhone(String phone);

    void insertIntoUser(User user);

    void updatePwd(User userSearch);

    User findUnlock(User user);

    void changeStatus(User usersearch);

    void deleteUser(String uid);

    User selectUserById(String inSearchName);

    void updateUser(User user0);
}
