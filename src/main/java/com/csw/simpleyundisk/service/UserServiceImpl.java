package com.csw.simpleyundisk.service;

import com.csw.simpleyundisk.dao.UserDao;
import com.csw.simpleyundisk.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryUserByPhone(String phone) {

        return userDao.queryUserByPhone(phone);
    }

    @Override
    public void insertIntoUser(User user) {
        userDao.insertIntoUser(user);
    }

    @Override
    public void updatePwd(User userSearch) {
        userDao.updatePwd(userSearch);
    }

    @Override
    public User findUnlock(User user) {
        return userDao.findUnlock(user);
    }

    @Override
    public void changeStatus(User usersearch) {
        userDao.changeStatus(usersearch);
    }

    @Override
    public void deleteUser(String uid) {
        userDao.deleteUser(uid);
    }

    @Override
    public User selectUserById(String inSearchName) {
        return userDao.selectUserById(inSearchName);
    }

    @Override
    public void updateUser(User user0) {
        userDao.updateUser(user0);
    }

}
