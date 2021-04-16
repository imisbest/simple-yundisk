package com.csw.simpleyundisk.service;

import com.csw.simpleyundisk.entity.Black;

public interface BlackService {
    void insertInto(Black black);

    void deleteUser(String uid);

    Black queryByUid(String id);

    void updateCount(Black black);
}
