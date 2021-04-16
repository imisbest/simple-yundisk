package com.csw.simpleyundisk.service;

import com.csw.simpleyundisk.dao.ShareDao;
import com.csw.simpleyundisk.entity.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShareServiceImpl implements ShareService {
    @Autowired
    private ShareDao shareDao;

    @Override
    public List<Share> queryAllFile(String toid) {
        return shareDao.queryAllFile(toid);
    }

    @Override
    public List<Share> queryAllDir(String toid) {
        return shareDao.queryAllDir(toid);
    }

    @Override
    public void insert(Share share) {
        shareDao.insert(share);
    }

    @Override
    public Share queryById(String fid) {
        return shareDao.queryById(fid);
    }

    @Override
    public Share queryById2(String fid) {
        return shareDao.queryById2(fid);
    }

    @Override
    public void deleteById(String id) {
        shareDao.deleteById(id);
    }

    @Override
    public List<Share> queryAllFiles() {
        return shareDao.queryAllFiles();
    }

    @Override
    public List<Share> queryAllDirs() {
        return shareDao.queryAllDirs();
    }

    @Override
    public List<Share> queryAllFilesLike(String inSearchName) {
        return shareDao.queryAllFilesLike(inSearchName);
    }

    @Override
    public List<Share> queryAllDirsLikes(String inSearchName) {
        return shareDao.queryAllDirsLikes(inSearchName);
    }


}
