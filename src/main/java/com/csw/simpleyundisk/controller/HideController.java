package com.csw.simpleyundisk.controller;

import com.csw.simpleyundisk.entity.Dir;
import com.csw.simpleyundisk.entity.FileEntity;
import com.csw.simpleyundisk.entity.User;
import com.csw.simpleyundisk.service.DirService;
import com.csw.simpleyundisk.service.FileService;
import com.csw.simpleyundisk.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("hide")
public class HideController {
    @Autowired
    private DirService dirService;
    @Autowired
    private FileService fileService;

    @RequestMapping("queryAll")
    public String queryAll(HttpSession session, String hidePwd) {
        //hidePwd]" + hidePwd);
        User user = (User) session.getAttribute("user");
        //user]" + user);
        if (user == null) {
            return "redirect:/user/logOut";
        }
        if (user.getHidePwd() == null) {
            //你的隐藏密码为空，请去我的个人中心设置隐藏密码");
            session.setAttribute("message", "你的隐藏密码为空，请去我的个人中心设置隐藏密码");
            return "redirect:/jsp/front/main/detail/hide.jsp";
        } else if (hidePwd == null) {
            //对不起，你输入的密码为空");
            session.setAttribute("message", "对不起，你输入的密码为空");
            return "redirect:/jsp/front/main/detail/hide.jsp";
        }
        String pwd = MD5Utils.getPassword(hidePwd) + user.getHideSalt();
        //pwd]" + pwd);
        if (!pwd.equals(user.getHidePwd())) {
            //你输入的密码不正确，请重新输入,或者去我的个人中心重新设置密码");
            session.setAttribute("message", "你输入的密码不正确，请重新输入,或者去我的个人中心重新设置密码");
        } else {

            int status = 2;
            List<Dir> dirs = dirService.selectByStatus(status, user);
            List<FileEntity> fileEntities = fileService.selectByStatus(status, user);
            session.setAttribute("dirs", dirs);
            session.setAttribute("files", fileEntities);
        }
        return "redirect:/jsp/front/main/detail/hide.jsp";
    }


    @RequestMapping("getMessage")
    @ResponseBody
    public String getMessage(HttpSession session) {
        return (String) session.getAttribute("message");
    }

    /**
     * @param session
     * 值
     * @return
     * 返回值
     */
    @RequestMapping("cleMessage")
    public String cleMessage(HttpSession session) {
        //【cleMessage】");
        session.setAttribute("message", null);
        return "redirect:/jsp/front/loginHide.jsp";
    }

    /**
     * @param fileId
     * 文件id
     * @param session
     * 值
     * @return
     * 返回值
     */
    @RequestMapping("/yrdel")
    public String yrdel(String fileId, HttpSession session) {
        //文件移入隐藏文件夹方法￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥");
        FileEntity fileEntity = fileService.selectFileById(fileId);
        fileEntity.setStatus(2);
        fileService.updateStatus(fileEntity);
        session.setAttribute("message", "文件移入隐藏文件夹成功");
        return "redirect:/mydoc/queryOne";
    }

    /**
     * @param pid
     * 父id
     * @param session
     * 值
     * @return
     * 返回值
     */
    @RequestMapping("/yrdelDir")
    public String yrdelDir(String pid, HttpSession session) {
        //进入到文件夹移入隐藏文件夹方法&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        Dir dir1 = dirService.selectById(pid);
        dir1.setStatus(2);
        dirService.updateStatue(dir1);
        session.setAttribute("message", "文件夹移入隐藏文件夹成功");
        return "redirect:/mydoc/queryOne";
    }

}
