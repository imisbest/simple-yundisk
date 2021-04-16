package com.csw.simpleyundisk.service;

import com.csw.simpleyundisk.entity.FileEntity;
import com.csw.simpleyundisk.entity.User;

import java.util.List;

public interface FileService {
    void addFile(FileEntity fileEntity);

    FileEntity selectFileById(String fileId);

    void deleteFileById(String fileId);

    void deleteFileByFolderId(String id);


    void updateCount(int newCount, String id);

    List<FileEntity> queryAllById(String did, User user, int status);

    List<FileEntity> selectFileByPidAndMd5(String id, String checkMd5);

    List<FileEntity> selectByMd5(String checkMd5);

    List<FileEntity> queryFileByPid(String id);

    List<FileEntity> selectAllFileByFatherFolder(String path, User user, int status);

    void updatePathAndPid(FileEntity fileEntity);

    List<FileEntity> queryAllByIddirYd(String firstDid, User user, String status, String dirYd);

    List<FileEntity> queryAllByinSearchName(String inSearchName, User user, int status);

    void updateNameById(FileEntity fileEntity);


    FileEntity queryByUserStatusNamePath(User user, int status, FileEntity fileEntity);

    void updatePathById(FileEntity fileEntity);

    void updateDid(String fileId, String filePid);

    List<FileEntity> selectByStatus(int status, User user);

    void updateStatus(FileEntity fileEntity);

    List<FileEntity> queryLater(User user, int status);

    void updateTime(FileEntity fileEntity);
}
