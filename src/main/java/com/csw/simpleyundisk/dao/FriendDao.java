package com.csw.simpleyundisk.dao;

import com.csw.simpleyundisk.entity.Friend;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendDao {
    List<Friend> selectByUid(@Param("id") String id);

    Friend queryByUidFid(@Param("uid") String uid,
                         @Param("fid") String fid);

    void insertFriend(@Param("friend1") Friend friend1);

    void deleteById(@Param("fid") String fid);

    Friend selectById(@Param("id") String id);

    Friend selectUidFid(@Param("uid") String uid,
                        @Param("fid") String fid);
}
