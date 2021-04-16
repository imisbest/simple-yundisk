package com.csw.simpleyundisk.service;

import com.csw.simpleyundisk.entity.Dir;
import com.csw.simpleyundisk.entity.User;

import java.util.List;

public interface DirService {
    void addRootDir(Dir dir);

    Dir selectById(String did);


    Dir selectDirPath(String path, String s);

    List<Dir> selectAllFolderByFatherFolder(String path, String id);

    void deleteFolderById(String id);

    Dir selectByName(String name);

    List<Dir> queryAllById(String did, User user, int status);

    Dir queryByPidAndName(String did, String fileName);

    Dir queryFistDir(String uid, String pid, int status);

    List<Dir> selectAllFolderBy(String path0, User user, int status);

    List<Dir> queryAllByIddirYd(String firstDid, User user, int status, String dirYd);

    Dir queryByIdPathUidStatus(Dir dirjudge0);


    Dir selectByNamePid(User user, int status, String patha, String name);

    List<Dir> queryAllByinSearchName(String inSearchName, User user, int status);

    Dir queryByUserStatusNamePath(User user, int status, Dir dir);

    void updateNameById(Dir dir);

    void updatePathById(Dir dir2);

    void updatePid(String dir0Id, String dir0Pid);

    List<Dir> selectByStatus(int status, User user);

    void updateStatue(Dir dir1);
}
