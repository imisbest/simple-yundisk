package com.csw.simpleyundisk.service;

import com.csw.simpleyundisk.dao.BlackDao;
import com.csw.simpleyundisk.entity.Black;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BlackServiceImpl implements BlackService {
    @Autowired
    private BlackDao blackDao;

    @Override
    public void insertInto(Black black) {
        blackDao.insertInto(black);
    }

    @Override
    public void deleteUser(String uid) {
        blackDao.deleteUser(uid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Black queryByUid(String id) {

        return blackDao.queryByUid(id);
    }

    @Override
    public void updateCount(Black black) {
        blackDao.updateCount(black);
    }
}
