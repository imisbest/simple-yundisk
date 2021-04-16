package com.csw.simpleyundisk.service;

import com.csw.simpleyundisk.entity.Friend;

import java.util.List;

public interface FriendService {

    List<Friend> selectByUid(String id);

    Friend queryByUidFid(String id, String id1);

    void insertFriend(Friend friend1);

    void deleteById(String fid);

    Friend selectById(String fid);

    Friend selectUidFid(String uid, String fid);
}
