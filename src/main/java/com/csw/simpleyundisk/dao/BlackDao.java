package com.csw.simpleyundisk.dao;

import com.csw.simpleyundisk.entity.Black;
import org.springframework.data.repository.query.Param;

public interface BlackDao {
    void insertInto(@Param("black") Black black);

    void deleteUser(@Param("uid") String uid);

    Black queryByUid(@Param("id") String id);

    void updateCount(@Param("black") Black black);
}
