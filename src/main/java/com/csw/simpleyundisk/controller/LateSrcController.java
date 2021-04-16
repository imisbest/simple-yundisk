package com.csw.simpleyundisk.controller;

import com.csw.simpleyundisk.entity.FileEntity;
import com.csw.simpleyundisk.entity.User;
import com.csw.simpleyundisk.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Administrator
 */
@RequestMapping("latest")
@Controller
public class LateSrcController {
    @Autowired
    private FileService fileService;


    @RequestMapping("queryAll")
    public String queryAll(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/logOut";
        }
        int ststus = 1;
        //查找，当前用户，正常状态下的文件
        List<FileEntity> fileEntities = fileService.queryLater(user, ststus);
        session.setAttribute("files", fileEntities);
        return "redirect:/jsp/front/main/detail/later.jsp";
    }


}
