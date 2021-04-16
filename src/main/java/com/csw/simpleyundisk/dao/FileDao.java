package com.csw.simpleyundisk.dao;

import com.csw.simpleyundisk.entity.FileEntity;
import com.csw.simpleyundisk.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileDao {
    void addFile(@Param("file") FileEntity fileEntity);

    FileEntity selectFileById(@Param("fileId") String fileId);

    void deleteFileById(@Param("fileId") String fileId);

    void deleteFileByFolderId(@Param("id") String id);

    void updateCount(@Param("newCount") int newCount,
                     @Param("id") String id);

    List<FileEntity> queryAllById(@Param("did") String did,
                                  @Param("user") User user,
                                  @Param("status") int status);

    List<FileEntity> selectFileByPidAndMd5(@Param("id") String id, @Param("checkMd5") String checkMd5);

    List<FileEntity> selectByMd5(@Param("checkMd5") String checkMd5);

    List<FileEntity> queryFileByPid(@Param("id") String id);

    List<FileEntity> selectAllFileByFatherFolder(@Param("path") String path,
                                                 @Param("user") User user,
                                                 @Param("status") int status);

    void updatePathAndPid(@Param("file") FileEntity fileEntity);

    List<FileEntity> queryAllByIddirYd(@Param("did") String did,
                                       @Param("user") User user,
                                       @Param("status") String status,
                                       @Param("dirYd") String dirYd);

    List<FileEntity> queryAllByinSearchName(@Param("inSearchName") String inSearchName,
                                            @Param("user") User user,
                                            @Param("status") int status);

    void updateNameById(@Param("file") FileEntity fileEntity);

    FileEntity queryByUserStatusNamePath(@Param("user") User user,
                                         @Param("status") int status,
                                         @Param("file") FileEntity fileEntity);

    void updatePathById(@Param("file") FileEntity fileEntity);

    void updateDid(@Param("id") String id,
                   @Param("pid") String pid);

    List<FileEntity> selectByStatus(@Param("status") int status,
                                    @Param("user") User user);

    void updateStatus(@Param("file") FileEntity fileEntity);

    List<FileEntity> queryLater(@Param("user") User user,
                                @Param("ststus") int status);

    void updateTime(@Param("file") FileEntity fileEntity);
}
