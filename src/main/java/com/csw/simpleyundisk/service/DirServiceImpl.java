package com.csw.simpleyundisk.service;

import com.csw.simpleyundisk.dao.DirDao;
import com.csw.simpleyundisk.entity.Dir;
import com.csw.simpleyundisk.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DirServiceImpl implements DirService {
    @Autowired
    private DirDao dirDao;

    @Override
    public void addRootDir(Dir dir) {
        dirDao.addRootDir(dir);
    }

    @Override
    public Dir selectById(String did) {
        return dirDao.selectById(did);
    }

    @Override
    public Dir selectDirPath(String path, String s) {
        return dirDao.selectDirPath(path, s);
    }

    @Override
    public List<Dir> selectAllFolderByFatherFolder(String path, String id) {
        List<Dir> dirs;
        dirs = dirDao.selectAllFolderByFatherFolder(path, id);
        return dirs;
    }

    @Override
    public void deleteFolderById(String id) {
        dirDao.deleteFolderById(id);
    }

    @Override
    public Dir selectByName(String name) {
        return dirDao.selectByName(name);
    }

    @Override
    public List<Dir> queryAllById(String did, User user, int status) {
        return dirDao.queryAllById(did, user, status);
    }

    @Override
    public Dir queryByPidAndName(String did, String fileName) {
        return dirDao.queryByPidAndName(did, fileName);
    }

    @Override
    public Dir queryFistDir(String uid, String pid, int status) {
        return dirDao.queryFistDir(uid, pid, status);
    }

    @Override
    public List<Dir> selectAllFolderBy(String path0, User user, int status) {
        return dirDao.selectAllFolderBy(path0, user, status);
    }

    @Override
    public List<Dir> queryAllByIddirYd(String firstDid, User user, int status, String dirYd) {
        return dirDao.queryAllByIddirYd(firstDid, user, status, dirYd);
    }

    @Override
    public Dir queryByIdPathUidStatus(Dir dirjudge0) {
        return dirDao.queryByIdPathUidStatus(dirjudge0);
    }

    @Override
    public Dir selectByNamePid(User user, int status, String patha, String name) {
        return dirDao.selectByNamePid(user, status, patha, name);
    }

    @Override
    public List<Dir> queryAllByinSearchName(String inSearchName, User user, int status) {
        return dirDao.queryAllByinSearchName(inSearchName, user, status);
    }

    @Override
    public Dir queryByUserStatusNamePath(User user, int status, Dir dir) {
        return dirDao.queryByUserStatusNamePath(user, status, dir);
    }

    @Override
    public void updateNameById(Dir dir) {
        dirDao.updateNameById(dir);
    }

    @Override
    public void updatePathById(Dir dir2) {
        dirDao.updatePathById(dir2);
    }

    @Override
    public void updatePid(String dir0Id, String dir0Pid) {
        dirDao.updatePid(dir0Id, dir0Pid);
    }

    @Override
    public List<Dir> selectByStatus(int status, User user) {
        return dirDao.selectByStatus(status, user);
    }

    @Override
    public void updateStatue(Dir dir1) {
        dirDao.updateStatue(dir1);
    }


}
