package com.csw.simpleyundisk.controller;

import com.csw.simpleyundisk.entity.Dir;
import com.csw.simpleyundisk.entity.FileEntity;
import com.csw.simpleyundisk.entity.User;
import com.csw.simpleyundisk.service.DirService;
import com.csw.simpleyundisk.service.FileService;
import com.csw.simpleyundisk.util.DeleteAllByPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@RequestMapping("recover")
@Controller
public class RecoverController {
    @Autowired
    private FileService fileService;
    @Autowired
    private DirService dirService;

    @RequestMapping("queryAll")
    public String queryAll(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/logOut";
        }
        int status = 0;
        List<Dir> dirs = dirService.selectByStatus(status, user);
        List<FileEntity> fileEntities = fileService.selectByStatus(status, user);
        session.setAttribute("dirs", dirs);
        session.setAttribute("files", fileEntities);
        return "redirect:/jsp/front/main/detail/recover.jsp";
    }

    @RequestMapping("recorverFile")
    public String recorverFile(String fileId) {
        FileEntity fileEntity = fileService.selectFileById(fileId);
        fileEntity.setStatus(1);
        fileService.updateStatus(fileEntity);
        return "redirect:/recover/queryAll";
    }

    @RequestMapping("recorverDir")
    public String recorverDir(String pid) {

        Dir dir1 = dirService.selectById(pid);
        dir1.setStatus(1);
        dirService.updateStatue(dir1);
        return "redirect:/recover/queryAll";
    }

    @RequestMapping("/del")
    public String del(String fileId) {
        //进入删除文件方法￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥");
        FileEntity fileEntity = fileService.selectFileById(fileId);
        //file]" + fileEntity);
        //完整路径
        String filepath = fileEntity.getPath() + "\\" + fileEntity.getNewName();
        //filepath]" + filepath);
        File file1 = new File(filepath);
        if (file1.exists()) {
            file1.delete();
            //删除成功");
        }
        fileService.deleteFileById(fileId);
        return "redirect:/mydoc/queryOne";
    }


    @RequestMapping("/delDir")
    public String delDir(String pid, HttpSession session) {
        //进入到删除文件夹方法&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        User user = (User) session.getAttribute("user");
        Dir dir = dirService.selectById(pid);
        //dir]" + dir);

        String path = dir.getPath() + "\\" + dir.getName();
        //path]" + path);
        String path0 = path.replaceAll("\\\\", "\\\\\\\\\\\\\\\\");
        //path0]" + path0);
        //找出所有的子目录///
        List<Dir> targetFolder = dirService.selectAllFolderByFatherFolder(path0, dir.getId());


        //targetFolder]" + targetFolder);
        //删除根目录下的文件
        fileService.deleteFileByFolderId(dir.getId());
        //删除子目录下的文件和文件夹
        for (Dir f : targetFolder) {
            //【f】" + f);
            fileService.deleteFileByFolderId(f.getId());
            dirService.deleteFolderById(f.getId());
        }
        //最后删除目标文件夹
        dirService.deleteFolderById(pid);

        File fulldir = new File(path);
        //找出所有的子目录///
        //服务器执行删除实际文件夹
        DeleteAllByPath.deleteFile(fulldir);
        //删除成功");
        return "redirect:/mydoc/queryOne";
    }

    @RequestMapping("getMessage")
    @ResponseBody
    public String getMessage(HttpSession session) {
        return (String) session.getAttribute("message");
    }


    @RequestMapping("cleMessage")
    public String cleMessage(HttpSession session) {
        //【cleMessage】");
        session.setAttribute("message", null);
        return "redirect:/recorver/queryAll";
    }
}
