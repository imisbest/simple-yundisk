package com.csw.simpleyundisk.service;

import com.csw.simpleyundisk.dao.FriendDao;
import com.csw.simpleyundisk.entity.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendDao friendDao;

    @Override
    public List<Friend> selectByUid(String id) {
        return friendDao.selectByUid(id);
    }

    @Override
    public Friend queryByUidFid(String id, String id1) {
        return friendDao.queryByUidFid(id, id1);
    }

    @Override
    public void insertFriend(Friend friend1) {
        friendDao.insertFriend(friend1);
    }

    @Override
    public void deleteById(String fid) {
        friendDao.deleteById(fid);
    }

    @Override
    public Friend selectById(String fid) {
        return friendDao.selectById(fid);
    }

    @Override
    public Friend selectUidFid(String uid, String fid) {
        return friendDao.selectUidFid(uid, fid);

    }

}
