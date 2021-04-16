package com.csw.simpleyundisk.dao;

import com.csw.simpleyundisk.entity.Share;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShareDao {
    List<Share> queryAllFile(@Param("toid") String toid);

    List<Share> queryAllDir(@Param("toid") String toid);

    void insert(@Param("share") Share share);

    Share queryById(@Param("id") String id);

    Share queryById2(@Param("id") String id);

    void deleteById(@Param("id") String id);

    List<Share> queryAllFiles();

    List<Share> queryAllDirs();

    List<Share> queryAllFilesLike(@Param("name") String name);

    List<Share> queryAllDirsLikes(@Param("name") String name);
}
