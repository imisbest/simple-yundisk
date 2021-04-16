package com.csw.simpleyundisk.service;

import com.csw.simpleyundisk.entity.Share;

import java.util.List;

public interface ShareService {
    List<Share> queryAllFile(String toid);

    List<Share> queryAllDir(String toid);

    void insert(Share share);

    Share queryById(String fid);

    Share queryById2(String fid);

    void deleteById(String id);

    List<Share> queryAllFiles();

    List<Share> queryAllDirs();

    List<Share> queryAllFilesLike(String inSearchName);

    List<Share> queryAllDirsLikes(String inSearchName);

}
