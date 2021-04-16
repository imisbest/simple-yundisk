package com.csw.simpleyundisk.service;

import com.csw.simpleyundisk.dao.FileDao;
import com.csw.simpleyundisk.entity.FileEntity;
import com.csw.simpleyundisk.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FileServiceImpl implements FileService {
    @Autowired
    private FileDao fileDao;

    @Override
    public void addFile(FileEntity fileEntity) {
        fileDao.addFile(fileEntity);
    }

    @Override
    public FileEntity selectFileById(String fileId) {
        return fileDao.selectFileById(fileId);
    }

    @Override
    public void deleteFileById(String fileId) {
        fileDao.deleteFileById(fileId);
    }

    @Override
    public void deleteFileByFolderId(String id) {
        fileDao.deleteFileByFolderId(id);
    }


    @Override
    public void updateCount(int newCount, String id) {
        fileDao.updateCount(newCount, id);
    }

    @Override
    public List<FileEntity> queryAllById(String did, User user, int status) {
        return fileDao.queryAllById(did, user, status);
    }

    @Override
    public List<FileEntity> selectFileByPidAndMd5(String id, String checkMd5) {
        return fileDao.selectFileByPidAndMd5(id, checkMd5);
    }

    @Override
    public List<FileEntity> selectByMd5(String checkMd5) {
        return fileDao.selectByMd5(checkMd5);
    }

    @Override
    public List<FileEntity> queryFileByPid(String id) {
        return fileDao.queryFileByPid(id);
    }

    @Override
    public List<FileEntity> selectAllFileByFatherFolder(String path, User user, int status) {
        return fileDao.selectAllFileByFatherFolder(path, user, status);
    }

    @Override
    public void updatePathAndPid(FileEntity fileEntity) {
        fileDao.updatePathAndPid(fileEntity);
    }

    @Override
    public List<FileEntity> queryAllByIddirYd(String firstDid, User user, String status, String dirYd) {
        return fileDao.queryAllByIddirYd(firstDid, user, status, dirYd);
    }

    @Override
    public List<FileEntity> queryAllByinSearchName(String inSearchName, User user, int status) {
        return fileDao.queryAllByinSearchName(inSearchName, user, status);
    }

    @Override
    public void updateNameById(FileEntity fileEntity) {
        fileDao.updateNameById(fileEntity);
    }

    @Override
    public FileEntity queryByUserStatusNamePath(User user, int status, FileEntity fileEntity) {
        return fileDao.queryByUserStatusNamePath(user, status, fileEntity);
    }

    @Override
    public void updatePathById(FileEntity fileEntity) {
        fileDao.updatePathById(fileEntity);
    }

    @Override
    public void updateDid(String fileId, String filePid) {
        fileDao.updateDid(fileId, filePid);
    }

    @Override
    public List<FileEntity> selectByStatus(int status, User user) {
        return fileDao.selectByStatus(status, user);
    }

    @Override
    public void updateStatus(FileEntity fileEntity) {
        fileDao.updateStatus(fileEntity);
    }

    @Override
    public List<FileEntity> queryLater(User user, int status) {
        return fileDao.queryLater(user, status);
    }

    @Override
    public void updateTime(FileEntity fileEntity) {
        fileDao.updateTime(fileEntity);
    }


}
