package com.csw.simpleyundisk.dao;

import com.csw.simpleyundisk.entity.User;
import org.springframework.data.repository.query.Param;

public interface UserDao {
    User queryUserByPhone(@Param("phone") String phone);

    void insertIntoUser(@Param("user") User user);

    void updatePwd(@Param("userSearch") User userSearch);

    User findUnlock(@Param("user") User user);

    void changeStatus(@Param("usersearch") User usersearch);

    void deleteUser(@Param("uid") String uid);

    User selectUserById(@Param("id") String id);

    void updateUser(@Param("user") User user);
}
