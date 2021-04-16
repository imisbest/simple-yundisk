package com.csw.simpleyundisk.controller;

import com.csw.simpleyundisk.controller.chongFu.SaveFile;
import com.csw.simpleyundisk.entity.*;
import com.csw.simpleyundisk.service.*;
import com.csw.simpleyundisk.util.TextCopyFileAndMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequestMapping("friend")
@Controller
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShareService shareService;
    @Autowired
    private FileService fileService;
    @Autowired
    private DirService dirService;


    @RequestMapping("friQue")
    public String friQue(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/logOut";
        }
        List<Friend> friendList = friendService.selectByUid(user.getId());
        //friendList]" + friendList.toString());


        List<Share> shareListfile = shareService.queryAllFile(user.getId());
        //shareListfile]" + shareListfile.toString());
        for (Share share : shareListfile) {
            if (share.getFileEntity() == null) {
                shareService.deleteById(share.getId());
            }
        }
        shareListfile = shareService.queryAllFile(user.getId());

        List<Share> shareListDir = shareService.queryAllDir(user.getId());
        //shareListDir]" + shareListDir.toString());
        for (Share share : shareListDir) {
            if (share.getDir() == null) {
                shareService.deleteById(share.getId());
            }
        }
        shareListDir = shareService.queryAllDir(user.getId());
        session.setAttribute("friendList", friendList);
        session.setAttribute("shareListfile", shareListfile);
        session.setAttribute("shareListDir", shareListDir);
        return "redirect:/jsp/front/main/detail/friend.jsp";
    }

    /**
     * @param session
     * @param inSearchName
     * @param aa
     * @return
     */
    @RequestMapping("serFri")
    public String serFri(HttpSession session, String inSearchName, String aa) {
        //aa】" + aa);
        if (aa == null) {
            //inSearchName]" + inSearchName);
            if (!"".equals(inSearchName)) {
                session.setAttribute("serFri", inSearchName);
            } else {
                session.setAttribute("message", "输入为空，请检查后重新输入");
                session.setAttribute("serFri", null);
            }
            return "redirect:/friend/friQue";
        } else if (aa.equals("0")) {
            session.setAttribute("message", "你已经取消操作");
            session.setAttribute("serFri", null);
            return "redirect:/friend/friQue";
        } else if (aa.equals("1")) {
            //aa不为空");
            String fuid = (String) session.getAttribute("serFri");
            User user = (User) session.getAttribute("user");
            User fuser = userService.selectUserById(fuid);
            if (fuser == null) {
                session.setAttribute("message", "你说查找的用户不存在");
                //此用户不存在");
                session.setAttribute("serFri", null);
                return "redirect:/friend/friQue";
            } else {
                //判断好友里有没有这个好友
                Friend friend = friendService.queryByUidFid(user.getId(), fuid);
                if (friend != null) {
                    session.setAttribute("message", "已经有这个好友");
                    //有这个好友不在进行添加操作");
                    session.setAttribute("serFri", null);
                    return "redirect:/friend/friQue";
                } else {
                    Friend friend1 = new Friend();
                    friend1.setId(UUID.randomUUID().toString());
                    friend1.setUser(user);
                    friend1.setFuser(fuser);
                    //即将添加好友friend1】" + friend1);
                    friendService.insertFriend(friend1);
                    //反向添加好友
                    Friend friend2 = new Friend();
                    friend2.setId(UUID.randomUUID().toString());
                    friend2.setUser(fuser);
                    friend2.setFuser(user);
                    //即将添加好友friend2】" + friend2);
                    friendService.insertFriend(friend2);

                    //添加好友成功");
                    session.setAttribute("message", "添加好友成功");
                    session.setAttribute("serFri", null);
                    return "redirect:/friend/friQue";
                }
            }
        }
        return "redirect:/friend/friQue";
    }

   
    @RequestMapping("celFriend")
    public String celFriend(HttpSession session, String fid) {
        //fid]" + fid);

        Friend friend = friendService.selectById(fid);
        friendService.deleteById(fid);
        //friend]" + friend);
        Friend friend1 = friendService.selectUidFid(friend.getFuser().getId(), friend.getUser().getId());
        //friend1]" + friend1);
        friendService.deleteById(friend1.getId());
        session.setAttribute("message", "删除好友成功");
        return "redirect:/friend/friQue";
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
        return "redirect:/friend/friQue";
    }

    @RequestMapping("sharefile")
    public String share(String sharefileId, HttpSession session, String uid, String aa) {
        //sharefileId]" + sharefileId);
        User user = (User) session.getAttribute("user");
        if (sharefileId != null) {
            //文件sharefileId不为空");
            List<Friend> friendList = friendService.selectByUid(user.getId());
            session.setAttribute("friendList", friendList);
            session.setAttribute("sharefile", sharefileId);
            return "redirect:/mydoc/queryOne";
        } else {
            if (aa != null) {
                session.setAttribute("friendList", null);
                session.setAttribute("sharefile", null);
                session.setAttribute("message", "你已经取消分享");
                return "redirect:/mydoc/queryOne";
            } else {
                //文件sharefileId空");
                //uid】" + uid);
                sharefileId = (String) session.getAttribute("sharefile");
                FileEntity fileEntity = fileService.selectFileById(sharefileId);
                Share share = new Share();
                share.setId(UUID.randomUUID().toString());
                share.setFromId(user.getId());
                share.setToId(uid);
                share.setFileEntity(fileEntity);
                //share" + share);
                shareService.insert(share);
                //分享成功");
                fileEntity.setOptionTime(new Date());
                fileService.updateTime(fileEntity);
                session.setAttribute("friendList", null);
                session.setAttribute("sharefile", null);
                session.setAttribute("message", "分享成功");
                return "redirect:/mydoc/queryOne";
            }
        }
    }

    @RequestMapping("onsave")
    public String onsave(String fid, HttpSession session, String aa) {
        //要保存的分享fid]" + fid);

        User user = (User) session.getAttribute("user");
        if (fid != null) {
            session.setAttribute("onsave", fid);
            session.setAttribute("message", "去选择保存路径");
            return "redirect:/mydoc/queryOne";
        } else {
            //fid是空的");
            //aa]" + aa);
            if (aa.equals("0")) {
                session.setAttribute("onsave", null);
                return "redirect:/mydoc/queryOne";
            } else if (aa.equals("1")) {
                fid = (String) session.getAttribute("onsave");
                Share share = shareService.queryById(fid);
                FileEntity fileEntity = fileService.selectFileById(share.getFileEntity().getId());
                String folderid = (String) session.getAttribute("did");
                //调用重复方法
                SaveFile.saveFile(session, user, fileEntity, folderid);
                //保存成功
                session.setAttribute("onsave", null);
                session.setAttribute("message", "保存成功");
                return "redirect:/mydoc/queryOne";
            }
        }
        return "redirect:/mydoc/queryOne";
    }

    @RequestMapping("onsaveDir")
    public String onsaveDir(String fid, HttpSession session, String aa) {
        //要保存的分享fid]" + fid);

        User user = (User) session.getAttribute("user");
        if (fid != null) {
            session.setAttribute("onsaveDir", fid);
            session.setAttribute("message", "去选择保存路径");
            return "redirect:/mydoc/queryOne";
        } else {
            //fid是空的");
            //aa]" + aa);
            if (aa.equals("0")) {
                session.setAttribute("onsaveDir", null);
                return "redirect:/mydoc/queryOne";
            } else if (aa.equals("1")) {
                fid = (String) session.getAttribute("onsaveDir");
                //fid]" + fid);
                Share share = shareService.queryById2(fid);
                //share]" + share);
                User userold = userService.selectUserById(share.getFromId());
                //要移动的文件夹
                Dir dir1 = dirService.selectById(share.getDir().getId());
                //dir1]" + dir1);
                String folderid = (String) session.getAttribute("did");
                //folderid]" + folderid);
                //folderid]" + folderid);
                //目标文件夹
                Dir dir = dirService.selectById(folderid);
                //dir]" + dir);
                //保存文件


                if (dir.getId().equals(dir1.getDid())) {
                    //有相同目录不移动
                    session.setAttribute("message", "已经复制过了");
                    session.setAttribute("onsaveDir", null);
                    return "redirect:/mydoc/queryOne";
                }
                int status = 1;
                String patha = dir.getPath() + "\\" + dir.getName();
                Dir dirSearch = dirService.selectByNamePid(user, status, patha, dir1.getName());
                if (dirSearch != null) {
                    //同一目录不移动
                    session.setAttribute("message", "已经复制过了");
                    session.setAttribute("onsaveDir", null);
                    return "redirect:/mydoc/queryOne";
                }
                String path = dir.getPath() + "\\" + dir.getName();
                String path1 = dir1.getPath() + "\\" + dir1.getName();
                //path]" + path);
                String path0 = path1.replaceAll("\\\\", "\\\\\\\\\\\\\\\\");
                //path0]" + path0);


                //要保证这两个方法都能查到pid,uid
                //2.查询所有

                List<FileEntity> fileEntityList = fileService.selectAllFileByFatherFolder(path0, userold, status);
                //文件】】" + fileEntity);
                List<Dir> dirList = dirService.selectAllFolderBy(path0, userold, status);
                //dir】】" + dir2);
                //$dir1$" + dir1);
                dir1.setDid(dir.getId());
                //$dir1$" + dir1);
                dirList.add(dir1);
                String dir0Id = dir1.getId() + "1";
                String dir0Pid = dir1.getDid();

                //数据库操作
                //5.数据库复制信息
                StringBuilder pathFirst;
                for (FileEntity fileEntity : fileEntityList) {
                    //文件操作】】" + fileEntity);
                }
                for (FileEntity fileEntity : fileEntityList) {
                    //FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
                    pathFirst = new StringBuilder(dir.getPath() + "\\" + dir.getName());
                    String[] ff = fileEntity.getPath().split("\\\\");
                    for (String s : ff) {
                        //ffs]" + s);
                    }

                    boolean judge = false;
                    for (int i = 0; i < ff.length; i++) {
                        //要移动文件夹的名字等于文件路径上的名字
                        if (ff[i].equals(dir1.getName())) {
                            //【进入到judge判断】 ");
                            judge = true;
                        }
                        if (judge) {
                            //judge]" + judge);
                            pathFirst.append("\\").append(ff[i]);
                        }
                    }
                    //新路径pathFirst]" + pathFirst);
                    //

                    //处理完成，
                    //进入处理数据库环节");
                    //更新数据
                    fileEntity.setId(fileEntity.getId() + "1");
                    fileEntity.setUser(user);
                    //原来的file]" + fileEntity);
                    fileEntity.setPath(pathFirst.toString());
                    //新的的file]" + fileEntity);
                    fileService.addFile(fileEntity);
                    //更新文件成功");
                    //一起添加的话，他的pid就找不到了
                    String filePid = fileEntity.getDir().getId() + "1";
                    String fileId = fileEntity.getId();
                    fileService.updateDid(fileId, filePid);
                }
                //文件夹操作dir】】" + dir2);
                for (Dir dir2 : dirList) {
                    //DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                    pathFirst = new StringBuilder(dir.getPath() + "\\" + dir.getName());
                    String[] ff = dir2.getPath().split("\\\\");
                    //ff]" + ff);
                    boolean judge = false;
                    for (int i = 0; i < ff.length; i++) {
                        if (ff[i].equals(dir1.getName())) {
                            judge = true;
                        }
                        if (judge) {
                            pathFirst.append("\\").append(ff[i]);
                        }
                    }
                    //新路径pathFirst]" + pathFirst);

                    //处理完成，
                    //进入处理数据库环节");
                    //更新数据
                    //原来的dir2]" + dir2);
                    dir2.setPath(pathFirst.toString());
                    dir2.setId(dir2.getId() + "1");
                    dir2.setUser(user);
                    dir2.setDid(dir2.getDid() + "1");

                    //Dir dir3 = dir2;对象是引用数据类型的

                    //【新的的目录2】" + dir2);
                    dirService.addRootDir(dir2);

                    //最后
                    //更新成功");
                }
                //最后还原一下原来要移动文件的pid
                dirService.updatePid(dir0Id, dir0Pid);
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                //1.服务器移动
                TextCopyFileAndMove.copyDir(path, path1);
                //服务器移动完成");

                //保存成功
                session.setAttribute("onsaveDir", null);
                session.setAttribute("message", "保存成功");
                return "redirect:/mydoc/queryOne";
            }
        }
        return "redirect:/mydoc/queryOne";
    }

    @RequestMapping("shareDir")
    public String shareDir(String ShareDirId, HttpSession session, String uid, String aa) {
        //ShareDirId]" + ShareDirId);
        User user = (User) session.getAttribute("user");
        if (ShareDirId != null) {
            //文件sharefileId不为空");
            List<Friend> friendList = friendService.selectByUid(user.getId());
            session.setAttribute("friendList", friendList);
            session.setAttribute("shareDir", ShareDirId);
            return "redirect:/mydoc/queryOne";
        } else {
            //aa]" + aa);
            if (aa != null) {
                session.setAttribute("friendList", null);
                session.setAttribute("shareDir", null);
                session.setAttribute("message", "你已经取消分享");
                return "redirect:/mydoc/queryOne";
            } else {
                //文件sharefileId空");
                //uid】" + uid);
                ShareDirId = (String) session.getAttribute("shareDir");
                Dir dir = dirService.selectById(ShareDirId);
                Share share = new Share();
                share.setId(UUID.randomUUID().toString());
                share.setFromId(user.getId());
                share.setToId(uid);
                share.setDir(dir);
                //share" + share);
                shareService.insert(share);
                //分享成功");
                session.setAttribute("friendList", null);
                session.setAttribute("shareDir", null);
                session.setAttribute("message", "分享成功");
                return "redirect:/mydoc/queryOne";
            }

        }
    }
}
