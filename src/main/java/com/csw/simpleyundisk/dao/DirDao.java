package com.csw.simpleyundisk.dao;

import com.csw.simpleyundisk.entity.Dir;
import com.csw.simpleyundisk.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DirDao {
    void addRootDir(@Param("dir") Dir dir);

    Dir selectById(@Param("did") String did);

    Dir selectDirByDidAndName(@Param("pid") String id,
                              @Param("name") String name);

    Dir selectDirPath(@Param("path") String path,
                      @Param("name") String name);

    List<Dir> selectAllFolderByFatherFolder(@Param("path") String path,
                                            @Param("id") String id);

    void deleteFolderById(@Param("id") String id);

    Dir selectByName(@Param("name") String name);

    List<Dir> queryAllById(@Param("did") String did,
                           @Param("user") User user,
                           @Param("status") int status);

    Dir queryByPidAndName(@Param("did") String did,
                          @Param("fileName") String fileName);

    Dir queryFistDir(@Param("uid") String uid,
                     @Param("pid") String pid,
                     @Param("status") int status);

    List<Dir> selectAllFolderBy(@Param("path0") String path0,
                                @Param("user") User user,
                                @Param("status") int status);

    List<Dir> queryAllByIddirYd(@Param("did") String did,
                                @Param("user") User user,
                                @Param("status") int status,
                                @Param("dirYd") String dirYd);

    Dir queryByIdPathUidStatus(@Param("dirjudge0") Dir dirjudge0);


    Dir selectByNamePid(
            @Param("user") User user,
            @Param("status") int status,
            @Param("path") String path,
            @Param("name") String name);

    List<Dir> queryAllByinSearchName(@Param("inSearchName") String inSearchName,
                                     @Param("user") User user,
                                     @Param("status") int status);

    Dir queryByUserStatusNamePath(@Param("user") User user,
                                  @Param("status") int status,
                                  @Param("dir") Dir dir);

    void updateNameById(@Param("dir") Dir dir);

    void updatePathById(@Param("dir2") Dir dir2);

    void updatePid(@Param("id") String id,
                   @Param("pid") String pid);

    List<Dir> selectByStatus(@Param("status") int status,
                             @Param("user") User user);

    void updateStatue(@Param("dir") Dir dir);
}
