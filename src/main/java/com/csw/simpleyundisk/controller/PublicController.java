package com.csw.simpleyundisk.controller;

import com.csw.simpleyundisk.controller.chongFu.SaveFile;
import com.csw.simpleyundisk.entity.Dir;
import com.csw.simpleyundisk.entity.FileEntity;
import com.csw.simpleyundisk.entity.Share;
import com.csw.simpleyundisk.entity.User;
import com.csw.simpleyundisk.service.DirService;
import com.csw.simpleyundisk.service.FileService;
import com.csw.simpleyundisk.service.ShareService;
import com.csw.simpleyundisk.service.UserService;
import com.csw.simpleyundisk.util.TextCopyFileAndMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("public")
@Controller
public class PublicController {
    @Autowired
    private ShareService shareService;
    @Autowired
    private FileService fileService;
    @Autowired
    private DirService dirService;
    @Autowired
    private UserService userService;

    @RequestMapping("queryAll")
    public String queryAll(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/user/logOut";
        }
        List<Share> shareListfiles = shareService.queryAllFiles();
        //shareListfiles]" + shareListfiles.toString());
        for (Share share : shareListfiles) {
            if (share.getFileEntity() == null) {
                shareService.deleteById(share.getId());
            }
        }
        shareListfiles = shareService.queryAllFiles();

        List<Share> shareListDirs = shareService.queryAllDirs();
        //shareListDirs]" + shareListDirs.toString());
        for (Share share : shareListDirs) {
            if (share.getDir() == null) {
                shareService.deleteById(share.getId());
            }
        }
        shareListDirs = shareService.queryAllDirs();
        session.setAttribute("shareListfiles", shareListfiles);
        session.setAttribute("shareListDirs", shareListDirs);
        return "redirect:/jsp/front/main/detail/public.jsp";
    }

    @RequestMapping("getMessage")
    @ResponseBody
    public String getMessage(HttpSession session) {
        return (String) session.getAttribute("message");
    }


    @RequestMapping("cleMessage")
    public String cleMessage(HttpSession session) {
        //???cleMessage???");
        session.setAttribute("message", null);
        return "redirect:/public/queryAll";
    }

    @RequestMapping("onsaves")
    public String onsaves(String fid, HttpSession session, String aa) {
        //??????????????????fid]" + fid);

        User user = (User) session.getAttribute("user");
        if (fid != null) {
            session.setAttribute("onsaves", fid);
            session.setAttribute("message", "?????????????????????");
            return "redirect:/mydoc/queryOne";
        } else {
            //fid?????????");
            //aa]" + aa);
            if (aa.equals("0")) {
                session.setAttribute("onsaves", null);
                return "redirect:/mydoc/queryOne";
            } else if (aa.equals("1")) {
                fid = (String) session.getAttribute("onsaves");
                Share share = shareService.queryById(fid);
                FileEntity fileEntity = fileService.selectFileById(share.getFileEntity().getId());
                String folderid = (String) session.getAttribute("did");
                //??????????????????
                SaveFile.saveFile(session, user, fileEntity, folderid);
                //????????????
                session.setAttribute("onsaves", null);
                session.setAttribute("message", "????????????");
                return "redirect:/mydoc/queryOne";
            }
        }
        return "redirect:/mydoc/queryOne";
    }





    @RequestMapping("onsaveDirs")
    public String onsaveDirs(String fid, HttpSession session, String aa) {
        //??????????????????fid]" + fid);

        User user = (User) session.getAttribute("user");
        if (fid != null) {
            session.setAttribute("onsaveDirs", fid);
            session.setAttribute("message", "?????????????????????");
            return "redirect:/mydoc/queryOne";
        } else {
            //fid?????????");
            //aa]" + aa);
            if (aa.equals("0")) {
                session.setAttribute("onsaveDirs", null);
                return "redirect:/mydoc/queryOne";
            } else if (aa.equals("1")) {
                fid = (String) session.getAttribute("onsaveDirs");
               // //fid]" + fid);
                Share share = shareService.queryById2(fid);
                //share]" + share);
                User userold = userService.selectUserById(share.getFromId());
                //?????????????????????
                Dir dir1 = dirService.selectById(share.getDir().getId());
                //dir1]" + dir1);
                String folderid = (String) session.getAttribute("did");
                //folderid]" + folderid);
                //folderid]" + folderid);
                //???????????????
                Dir dir = dirService.selectById(folderid);
                //dir]" + dir);
                //????????????


                if (dir.getId().equals(dir1.getDid())) {
                    //????????????????????????
                    session.setAttribute("message", "??????????????????");
                    session.setAttribute("onsaveDirs", null);
                    return "redirect:/mydoc/queryOne";
                }
                int status = 1;
                String patha = dir.getPath() + "\\" + dir.getName();
                Dir dirSearch = dirService.selectByNamePid(user, status, patha, dir1.getName());
                if (dirSearch != null) {
                    //?????????????????????
                    session.setAttribute("message", "??????????????????");
                    session.setAttribute("onsaveDirs", null);
                    return "redirect:/mydoc/queryOne";
                }
                String path = dir.getPath() + "\\" + dir.getName();
                String path1 = dir1.getPath() + "\\" + dir1.getName();
                //path]" + path);
                String path0 = path1.replaceAll("\\\\", "\\\\\\\\\\\\\\\\");
                //path0]" + path0);


                //????????????????????????????????????pid,uid
                //2.????????????

                List<FileEntity> fileEntityList = fileService.selectAllFileByFatherFolder(path0, userold, status);
                for (FileEntity fileEntity : fileEntityList) {
                    //????????????" + fileEntity);
                }
                List<Dir> dirList = dirService.selectAllFolderBy(path0, userold, status);
                for (Dir dir2 : dirList) {
                    //dir??????" + dir2);
                }
                //$dir1$" + dir1);
                dir1.setDid(dir.getId());
                //$dir1$" + dir1);
                dirList.add(dir1);
                String dir0Id = dir1.getId() + "1";
                String dir0Pid = dir1.getDid();

                //???????????????
                //5.?????????????????????
                StringBuilder pathFirst;
                for (FileEntity fileEntity : fileEntityList) {
                    //??????????????????" + fileEntity);
                }
                for (FileEntity fileEntity : fileEntityList) {
                    ////FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
                    pathFirst = new StringBuilder(dir.getPath() + "\\" + dir.getName());
                    String[] ff = fileEntity.getPath().split("\\\\");
                    for (String s : ff) {
                        //ffs]" + s);
                    }

                    boolean judge = false;
                    for (String s : ff) {
                        //?????????????????????????????????????????????????????????
                        //ff[i]???" + s);
                        //dir1.getName()???" + dir1.getName());
                        if (s.equals(dir1.getName())) {
                            //????????????judge????????? ");
                            judge = true;
                        }
                        if (judge) {
                            //judge]" + true);
                            pathFirst.append("\\").append(s);
                        }
                    }
                    //?????????pathFirst]" + pathFirst);
                    //

                    //???????????????
                    //???????????????????????????");
                    //????????????
                    fileEntity.setId(fileEntity.getId() + "1");
                    fileEntity.setUser(user);
                    //?????????file]" + fileEntity);
                    fileEntity.setPath(pathFirst.toString());
                    //?????????file]" + fileEntity);
                    fileService.addFile(fileEntity);
                    //??????????????????");
                    //???????????????????????????pid???????????????
                    String filePid = fileEntity.getDir().getId() + "1";
                    String fileId = fileEntity.getId();
                    fileService.updateDid(fileId, filePid);
                }
                for (Dir dir2 : dirList) {
                    //???????????????dir??????" + dir2);
                }
                for (Dir dir2 : dirList) {
                    ////DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                    pathFirst = new StringBuilder(dir.getPath() + "\\" + dir.getName());
                    String[] ff = dir2.getPath().split("\\\\");
                    //ff]" + Arrays.toString(ff));
                    boolean judge = false;
                    for (String s : ff) {
                        if (s.equals(dir1.getName())) {
                            judge = true;
                        }
                        if (judge) {
                            pathFirst.append("\\").append(s);
                        }
                    }
                    //?????????pathFirst]" + pathFirst);

                    //???????????????
                    //???????????????????????????");
                    //????????????
                    //?????????dir2]" + dir2);
                    dir2.setPath(pathFirst.toString());
                    dir2.setId(dir2.getId() + "1");
                    dir2.setUser(user);
                    dir2.setDid(dir2.getDid() + "1");

                    //Dir dir3 = dir2;??????????????????????????????

                    //??????????????????2???" + dir2);
                    dirService.addRootDir(dir2);

                    //??????
                    //????????????");
                }
                //??????????????????????????????????????????pid
                dirService.updatePid(dir0Id, dir0Pid);
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                //1.???????????????
                TextCopyFileAndMove.copyDir(path, path1);
                //?????????????????????");

                //????????????
                session.setAttribute("onsaveDirs", null);
                session.setAttribute("message", "????????????");
                return "redirect:/mydoc/queryOne";
            }
        }
        return "redirect:/mydoc/queryOne";
    }

    @RequestMapping("inSearch")
    public String inSearch(String inSearchName, HttpSession session) {
        //inSearchName]" + inSearchName);
        List<Share> shareListfiles = shareService.queryAllFilesLike(inSearchName);
        //shareListfiles]" + shareListfiles);
        for (Share share : shareListfiles) {
            if (share.getFileEntity() == null) {
                shareService.deleteById(share.getId());
            }
        }
        shareListfiles = shareService.queryAllFilesLike(inSearchName);

        List<Share> shareListDirs = shareService.queryAllDirsLikes(inSearchName);
        //shareListDirs]" + shareListDirs);
        for (Share share : shareListDirs) {
            if (share.getDir() == null) {
                shareService.deleteById(share.getId());
            }
        }
        shareListDirs = shareService.queryAllDirsLikes(inSearchName);
        session.setAttribute("shareListfiles", shareListfiles);
        session.setAttribute("shareListDirs", shareListDirs);
        return "redirect:/jsp/front/main/detail/public.jsp";
    }

}
