package com.csw.simpleyundisk.controller.chongFu;

import com.csw.simpleyundisk.entity.Dir;
import com.csw.simpleyundisk.entity.FileEntity;
import com.csw.simpleyundisk.entity.User;
import com.csw.simpleyundisk.service.DirService;
import com.csw.simpleyundisk.service.FileService;
import com.csw.simpleyundisk.util.TextCopyFileAndMove;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

public class SaveFile {
    @Autowired
    private static DirService dirService;
    @Autowired
    private static FileService fileService;

    public static void saveFile(HttpSession session, User user, FileEntity fileEntity, String folderid) {
        //folderid]" + folderid);
        Dir dir = dirService.selectById(folderid);
        //保存文件
        //服务器移动
        String topath = dir.getPath() + "\\" + dir.getName();
        java.io.File file1 = new java.io.File(fileEntity.getPath() + "\\" + fileEntity.getNewName());
        String name = fileEntity.getNewName();
        TextCopyFileAndMove.copyFileToDir(topath, file1, name);
        //服务器移动完成");

        //进入处理数据库环节");
        //更新数据
        //原来的file]" + fileEntity);
        fileEntity.setId(UUID.randomUUID().toString());
        fileEntity.setPath(topath);
        fileEntity.setDir(dir);
        fileEntity.setUser(user);
        //新的的file]" + fileEntity);
        //md5
        List<FileEntity> filem = fileService.selectFileByPidAndMd5(dir.getId(), fileEntity.getCheckMd5());
        //filem]" + filem);
        if (filem.size() != 0) {
            //已有相同文件，不在进行操作");
        } else {
            //当前文件夹下没有相同的文件");
            fileService.addFile(fileEntity);
            session.setAttribute("fileYdid", null);
        }
        //更新成功");
    }
}
