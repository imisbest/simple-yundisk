package com.csw.simpleyundisk.controller;

import com.csw.simpleyundisk.controller.chongFu.BigSize;
import com.csw.simpleyundisk.entity.Dir;
import com.csw.simpleyundisk.entity.FileEntity;
import com.csw.simpleyundisk.entity.TiaoZhuan;
import com.csw.simpleyundisk.entity.User;
import com.csw.simpleyundisk.service.DirService;
import com.csw.simpleyundisk.service.FileService;
import com.csw.simpleyundisk.util.DeleteAllByPath;
import com.csw.simpleyundisk.util.TextCopyFileAndMove;
import com.csw.simpleyundisk.util.ZipUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipOutputStream;

@RequestMapping("mydoc")
@Controller
public class MyDocController {
    @Autowired
    private FileService fileService;

    @Autowired
    private DirService dirService;


    @RequestMapping("queryOne")
    public String queryOne(String did, HttpSession session) {
        //进入queryOne方法QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
        String dirYd = (String) session.getAttribute("DirYd");
        //dirYd]" + dirYd);
        //did]" + did);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            session.setAttribute("message", "用户不存在，退出登录");
            return "redirect:/user/logOut";
        }
        int status = 1;
        List<Dir> dirs;
        List<FileEntity> fileEntities;
        if (did != null) {
            //did!=null");
            if (did.equals("shouye")) {
                List<TiaoZhuan> tiaoZhuanList = new ArrayList<>();
                session.setAttribute("tzList", tiaoZhuanList);

                //did.equals(\"shoe\")");
                String uid = user.getId();
                //uid]" + uid);
                String pid = null;
                Dir dir = dirService.queryFistDir(uid, pid, status);
                String firstDid = dir.getId();
                //firstDid]" + firstDid);
                //user]" + user);
                //status]" + status);
                if (dirYd == null) {
                    dirs = dirService.queryAllById(firstDid, user, status);
                } else {
                    dirs = dirService.queryAllByIddirYd(firstDid, user, status, dirYd);
                }
                fileEntities = fileService.queryAllById(firstDid, user, status);
                session.setAttribute("did", firstDid);
            } else {
                List<TiaoZhuan> tzList = (List<TiaoZhuan>) session.getAttribute("tzList");

                //dis]" + did);
                //user]" + user);
                //status]" + status);
                if (dirYd == null) {
                    dirs = dirService.queryAllById(did, user, status);
                } else {
                    dirs = dirService.queryAllByIddirYd(did, user, status, dirYd);
                }
                fileEntities = fileService.queryAllById(did, user, status);
                session.setAttribute("did", did);
                /**/
                boolean judge = false;
                for (TiaoZhuan tz : tzList) {
                    if (tz.getId().equals(did)) {
                        judge = true;
                        break;
                    }
                }
                //judge]" + judge);
                if (!judge) {
                    //false");
                    Dir dir = dirService.selectById(did);
                    tzList.add(new TiaoZhuan(did, dir.getName()));
                } else {
                    //true");
                    List<TiaoZhuan> tiaoZhuanList = new ArrayList<>();
                    for (TiaoZhuan tz : tzList) {
                        tiaoZhuanList.add(tz);
                        if (tz.getId().equals(did)) {
                            break;
                        }

                    }
                    tzList = tiaoZhuanList;
                }
                //tzlist】" + tzList);
                session.setAttribute("tzList", tzList);
            }

        } else {
            //【did==null】");
            String did1 = (String) session.getAttribute("did");
            //dis]" + did1);
            //user]" + user);
            //status]" + status);
            if (dirYd == null) {
                dirs = dirService.queryAllById(did1, user, status);
            } else {
                dirs = dirService.queryAllByIddirYd(did1, user, status, dirYd);
            }
            fileEntities = fileService.queryAllById(did1, user, status);
        }
        //dirs]" + dirs);
        //files]" + fileEntities);
        //图片路径
        List<String> stringList = new ArrayList<>();
        for (FileEntity fileEntity : fileEntities) {
            //file]" + fileEntity);
            String path = fileEntity.getPath() + "\\" + fileEntity.getNewName();
            //psth]" + path);
            stringList.add(path);
        }
        session.setAttribute("stringList", stringList);
        session.setAttribute("dirs", dirs);
        session.setAttribute("files", fileEntities);
        //查询完成");
        return "redirect:/jsp/front/main/detail/mydoc.jsp";

    }

    /*首先查询所有，点击首页和点击个人中心查询到的都是一样的,点击每个文件夹相当于 相当于一次查询
    ，每次查询都要把最近的父文件夹id放到session中，查询的时候如何点击的是文件的，不做任何变化，
    如果是文件夹，则要查询所有，并且更新id*/


    @RequestMapping("addDir")
    public String addDir(String fileName, HttpSession session) {
        //fileName]" + fileName);

        if (!(fileName ==null)) {

            String did = (String) session.getAttribute("did");
            Dir dir = dirService.selectById(did);
            Dir dir3 = dirService.queryByPidAndName(did, fileName);
            if (dir3 != null) {
                session.setAttribute("message", "有相同目录不能再次添加");
            } else {
                //dir]" + dir);
                User user = (User) session.getAttribute("user");
                //user]" + user);
                String path = dir.getPath() + "\\" + dir.getName();
                //path]" + path);
                //0");

                Dir dir1 = new Dir();
                //1");
                dir1.setId(UUID.randomUUID().toString());
                //2");
                dir1.setName(fileName);
                //3");
                dir1.setPath(path);
                //4");
                dir1.setCount(0);
                //5");
                dir1.setCreateTime(new Date());
                //6");
                dir1.setStatus(1);
                //7");
                dir1.setIsShare(0);
                //8");
                dir1.setIcon("文件夹");
                //9");
                dir1.setDid(dir.getId());
                //10");
                dir1.setUser(user);
                //创建用户目录dir1]" + dir1);
                dirService.addRootDir(dir1);
                //本地创建
                File folder = new File(path + "\\" + fileName);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
            }
        }
        return "redirect:/mydoc/queryOne";
    }


    @RequestMapping("uploaddir")
    public String upload(MultipartFile[] files, HttpSession session) throws IOException {
        //files2]=" + Arrays.toString(files));

        String did = (String) session.getAttribute("did");
        Dir dir = dirService.selectById(did);
        String path0 = dir.getPath() + "\\" + dir.getName();
        User user = (User) session.getAttribute("user");

        for (MultipartFile aa : files) {
            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            String prepath = aa.getOriginalFilename();
            if (Objects.equals(prepath, "")) {
                return "redirect:/mydoc/queryOne";
            }
            //prepath]" + prepath);
            //包含文件夹名称和文件名称的数组
            assert prepath != null;
            String[] stringPath = prepath.split("/");
            //stringPath]" + Arrays.toString(stringPath));
            for (int i = 0; i <= stringPath.length - 1; i++) {
                //i全是文件夹
                //stringPath[i]" + stringPath[i]);
                if (i != stringPath.length - 1) {
                    //【//不是最后一个文件】");
                    StringBuilder path = new StringBuilder(path0);
                    for (int j = 0; j <= i; j++) {
                        //j全是文件夹
                        //path]" + path.toString());
                        //下面进行判断操作stringPath[j]" + stringPath[j]);
                        //根据路径和名字查询文件夹是否存在
                        Dir dir1 = dirService.selectDirPath(path.toString(), stringPath[j]);
                        if (dir1 != null) {
                            ////这个文件夹不存在，创建一个新的文件夹");
                            Dir dir2 = new Dir();
                            dir2.setId(UUID.randomUUID().toString());
                            dir2.setName(stringPath[i]);
                            dir2.setPath(path.toString());
                            dir2.setCount(0);
                            dir2.setCreateTime(new Date());
                            dir2.setStatus(1);
                            dir2.setIsShare(0);
                            dir2.setIcon("文件夹");
                            Dir dirP;
                            if (j == 0) {
                                dirP = dir;
                            } else {
                                StringBuilder pathP = new StringBuilder(path0);
                                //pathP0]" + pathP.toString());
                                //找父类的文件路径
                                for (int k = 0; k <= j - 1; k++) {
                                    //所在路径
                                    if (k - 1 >= 0) {
                                        pathP.append("\\").append(stringPath[k - 1]);
                                        //pathP.tostring]" + k + "//" + pathP.toString());
                                    }
                                }
                                //新文件夹父类stringPath[j]" + stringPath[j - 1]);
                                dirP = dirService.selectDirPath(pathP.toString(), stringPath[j - 1]);
                            }
                            //【dirP】" + dirP);
                            dir2.setDid(dirP.getId());
                            dir2.setUser(user);
                            //创建目录dir2]" + dir2);
                            dirService.addRootDir(dir2);
                            //本地创建
                            File folder = new File(path.toString() + "\\" + stringPath[i]);
                            if (!folder.exists()) {
                                folder.mkdirs();
                            }
                        }
                        if (i != j) {
                            //完整路径
                            path.append("\\").append(stringPath[j]);
                        }
                    }
                } else {
                    //【//最后一个文件】");
                    String originalFilename = stringPath[i];
                    //文件名//：" + originalFilename);
                    //文件类型//:" + aa.getContentType());
                    //文件大小//:" + aa.getSize());
                    //name//:" + aa.getName());

                    StringBuilder realPath = new StringBuilder(path0);
                    for (int j = 0; j <= i - 1; j++) {
                        //完整路径
                        realPath.append("\\").append(stringPath[j]);
                    }
                    //realPath//:" + realPath);

                    String newFileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                            + UUID.randomUUID().toString().replace("-", "");
                    String fileNameSuffix = "." + FilenameUtils.getExtension(originalFilename);
                    String newFileName = newFileNamePrefix + fileNameSuffix;

                    //抛异常,文件，完整目录
                    File filePathInfo = new File(realPath.toString(), newFileName);
                    aa.transferTo(filePathInfo);

                    FileEntity fileEntity = new FileEntity();
                    fileEntity.setId(UUID.randomUUID().toString());
                    fileEntity.setName(originalFilename);
                    fileEntity.setNewName(newFileName);
                    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                    fileEntity.setZFile(suffix);
                    fileEntity.setPath(realPath.toString());
                    //大小
                    String checkMd5 = BigSize.bigSize(aa, String.valueOf(realPath), newFileName, fileEntity, suffix);
                    StringBuilder pathP = new StringBuilder(path0);
                    //找父类的文件路径
                    for (int k = 0; k <= stringPath.length - 3; k++) {
                        //所在路径
                        pathP.append("\\").append(stringPath[k]);
                    }
                    //pathP.toString()]" + pathP.toString());
                    //stringPath[stringPath.length-2]" + stringPath[stringPath.length - 2]);
                    Dir dirP = dirService.selectDirPath(pathP.toString(), stringPath[stringPath.length - 2]);
                    fileEntity.setDir(dirP);
                    fileEntity.setUser(user);
                    //file]" + fileEntity);
                    //md5
                    List<FileEntity> filem = fileService.selectFileByPidAndMd5(dirP.getId(), checkMd5);
                    if (filem.size() == 0)
                        //当前文件夹下没有相同的文件");
                        fileService.addFile(fileEntity);
                    }
                }

            }
            //文件夹
                    /*文件名//：一级文件夹/1-1.txt
                    文件类型//:text/plain
                    文件大小//:3
                    name//:files
                    后缀//:.txt*/

                    /*文件名//：1.txt
                    文件类型//:text/plain
                    文件大小//:1
                    name//:aa
                    后缀//:.txt*/

        return "redirect:/mydoc/queryOne";
    }



    @RequestMapping("uploadfile")
    public String upload2(MultipartFile bb, HttpSession session) throws IOException {
        //bb]" + bb);
        //ln(bb.getOriginalFilename());
        if (Objects.equals(bb.getOriginalFilename(), "")) {
            session.setAttribute("message", "文件是空的");
            return "redirect:/mydoc/queryOne";
        }
        String did = (String) session.getAttribute("did");
        Dir dir = dirService.selectById(did);
        User user = (User) session.getAttribute("user");
        String originalFilename = bb.getOriginalFilename();
        //文件名//：" + originalFilename);
        //文件类型//:" + bb.getContentType());
        //文件大小//:" + bb.getSize());
        //name//:" + bb.getName());
        // //后缀//:" + bb.getOriginalFilename().substring(bb.getOriginalFilename().lastIndexOf(".")));
        String realPath = dir.getPath() + "\\" + dir.getName();
        //realPath//:" + realPath);

        String newFileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + UUID.randomUUID().toString().replace("-", "");
        String fileNameSuffix = "." + FilenameUtils.getExtension(originalFilename);
        String newFileName = newFileNamePrefix + fileNameSuffix;

        //抛异常,文件，完整目录
        File filePathInfo = new File(realPath, newFileName);
        bb.transferTo(filePathInfo);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(UUID.randomUUID().toString());
        fileEntity.setName(originalFilename);
        fileEntity.setNewName(newFileName);
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        fileEntity.setZFile(suffix);
        fileEntity.setPath(realPath);
        //大小
        String checkMd5 = BigSize.bigSize(bb, realPath, newFileName, fileEntity, suffix);

        fileEntity.setDir(dir);
        fileEntity.setUser(user);
        //file]" + fileEntity);
        //md5
        List<FileEntity> filem = fileService.selectFileByPidAndMd5(dir.getId(), checkMd5);
        //filem]" + filem);
        if (filem.size() != 0) {
            session.setAttribute("message", "有相同的文件不在上传");
            //已有相同文件，不在进行操作");
        } else {
            //当前文件夹下没有相同的文件");
            fileService.addFile(fileEntity);
        }
        /* fileService.addFile();*/
        return "redirect:/mydoc/queryOne";

    }



    @RequestMapping("/del")
    public String del(String fileId, HttpSession session) {
        //进入删除文件方法￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥");
        FileEntity fileEntity = fileService.selectFileById(fileId);
        fileEntity.setStatus(0);
        fileService.updateStatus(fileEntity);
        session.setAttribute("message", "删除文件成功");
        return "redirect:/mydoc/queryOne";
    }


    @RequestMapping("/delDir")
    public String delDir(String pid, HttpSession session) {
        //进入到删除文件夹方法&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        Dir dir1 = dirService.selectById(pid);
        dir1.setStatus(0);
        dirService.updateStatue(dir1);
        session.setAttribute("message", "删除文件夹成功");
        return "redirect:/mydoc/queryOne";
    }


    @RequestMapping("down")
    public String down(String id, String openStyle, HttpServletResponse response) throws Exception {
        //进入download方法%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        //id" + id);
        String aa = openStyle;
        //进来的openstyle]" + openStyle);
        if (openStyle == null) {
            openStyle = "attachment";
        } else {
            openStyle = "inline";
        }

        FileEntity fileEntity = fileService.selectFileById(id);
        String truePath = fileEntity.getPath() + "\\" + fileEntity.getNewName();
        //file]" + fileEntity);
        //truePath]" + truePath);
        File file1 = new File(truePath);
        //file1]" + file1);
        FileInputStream is = new FileInputStream(file1);
        String fileName = fileEntity.getName();

        response.setHeader("content-disposition", openStyle + ";filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream os = response.getOutputStream();
        IOUtils.copy(is, os);
        //noinspection deprecation
        IOUtils.closeQuietly(is);
        //noinspection deprecation
        IOUtils.closeQuietly(os);
        //下载成功");
        /*
          更新次数
         */
        //aa//:" + aa);
        if (aa == null) {
            //aaaaaaaaaaaaaaaaaaaaaa");
            int newCount = (int) (fileEntity.getCount() + 1);
            fileService.updateCount(newCount, fileEntity.getId());
        }
        fileEntity.setOptionTime(new Date());
        fileService.updateTime(fileEntity);
        return null;
    }


    @RequestMapping("downloadFiles")
    public void batchDownLoadDatumList(String id, HttpServletResponse response, HttpSession session) throws Exception {
        //进入到批量下载方法&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
        User user = (User) session.getAttribute("user");
        Dir dir = dirService.selectById(id);
        //dir]" + dir);
//
        String path = dir.getPath() + "\\" + dir.getName();
        //path]" + path);
        String path0 = path.replaceAll("\\\\", "\\\\\\\\\\\\\\\\");
        //path0]" + path0);
        //找出所有的子目录///
        int status = 1;
        List<FileEntity> filez = fileService.selectAllFileByFatherFolder(path0, user, status);
        //filez]" + filez);
        //压缩文件
        List<File> fileList = new ArrayList<>();
        for (FileEntity f : filez) {
            String srcPath = f.getPath();
            String targetPath = f.getPath();
            File tarFolder = new File(targetPath);
            if (!tarFolder.exists()) {
                tarFolder.mkdirs();
            }

            File srcFile = new File(srcPath + "\\" + f.getNewName());
            InputStream ins;
            OutputStream ots;

            ins = new FileInputStream(srcFile);
            String nPath = targetPath + "\\" + f.getName();
            ots = new FileOutputStream(nPath);
            int reader;
            byte[] readByte = new byte[2048];
            while ((reader = ins.read(readByte)) != -1) {
                ots.write(readByte, 0, reader);
            }

            ots.close();
            ins.close();
            fileList.add(new File(nPath));
        }

        //fileList]" + fileList);
        //【fileList.length】" + fileList.size());

        String fileName = dir.getName() + ".zip";
        //在服务器端创建打包下载的临时文件

        String outFilePath = dir.getPath() + "\\" + fileName;
        File file = new File(outFilePath);

        //文件输出流
        String openStyle = "attachment";
        response.setHeader("content-disposition", openStyle + ";filename=" + URLEncoder.encode(fileName, "UTF-8"));
        FileOutputStream outStream = new FileOutputStream(file);
        //压缩流
        ZipOutputStream toClient = new ZipOutputStream(outStream);
        ZipUtils.zipFile(fileList, toClient);
        toClient.close();
        outStream.close();
        ZipUtils.downloadZip(file, response);
    }


    @RequestMapping("fileYd")
    public String fileYd(String id, HttpSession session, int aa) {
        //id]" + id);
        if (id != null) {
            session.setAttribute("fileYdid", id);
        } else {
            id = (String) session.getAttribute("fileYdid");
            //aa]" + aa);
            if (aa == 0) {
                session.setAttribute("fileYdid", null);
            } else if (aa == 1) {
                FileEntity fileEntity = fileService.selectFileById(id);
                String did = (String) session.getAttribute("did");
                Dir dir = dirService.selectById(did);
                //服务器移动
                String topath = dir.getPath() + "\\" + dir.getName();
                File file1 = new File(fileEntity.getPath() + "\\" + fileEntity.getNewName());
                String name = fileEntity.getNewName();
                TextCopyFileAndMove.copyFileToDir(topath, file1, name);
                //服务器移动完成");
                //删除原来的文件
                //进入删除文件方法￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥");


                if (file1.exists()) {
                    file1.delete();
                    //删除成功");
                }
                fileService.deleteFileById(id);
                //处理完成，
                //进入处理数据库环节");
                //更新数据
                //原来的file]" + fileEntity);
                fileEntity.setPath(topath);
                fileEntity.setDir(dir);
                User user = (User) session.getAttribute("user");
                fileEntity.setUser(user);
                //新的的file]" + fileEntity);
                //md5
                List<FileEntity> filem = fileService.selectFileByPidAndMd5(dir.getId(), fileEntity.getCheckMd5());
                //filem]" + filem);
                if (filem.size() != 0) {
                    session.setAttribute("message", "已有相同文件，请检查后再次进行操作");
                    //已有相同文件，不在进行操作");
                } else {
                    //当前文件夹下没有相同的文件");
                    fileService.addFile(fileEntity);
                    session.setAttribute("fileYdid", null);
                    session.setAttribute("message", "文件移动成功");
                }
                //更新成功");
            }
        }
        return "redirect:/mydoc/queryOne";
    }


    @RequestMapping("DirYd")
    public String DirYd(String id, HttpSession session, int aa) {
        //id]" + id);
        User user = (User) session.getAttribute("user");
        if (id != null) {
            session.setAttribute("DirYd", id);
        } else {
            id = (String) session.getAttribute("DirYd");
            //aa]" + aa);
            if (aa == 0) {
                session.setAttribute("message", "你已经取消移动");
                session.setAttribute("DirYd", null);
            } else if (aa == 1) {
                String did = (String) session.getAttribute("did");
                //目标文件夹
                Dir dir = dirService.selectById(did);
                //要移动的文件夹
                Dir dir1 = dirService.selectById(id);
                if (dir.getId().equals(dir1.getDid())) {
                    //有相同目录不移动
                    session.setAttribute("message", "相同目录不进行移动");
                    session.setAttribute("DirYd", null);
                    return "redirect:/mydoc/queryOne";
                }
                int status = 1;
                String patha = dir.getPath() + "\\" + dir.getName();
                Dir dirSearch = dirService.selectByNamePid(user, status, patha, dir1.getName());
                if (dirSearch != null) {
                    //同一目录不移动
                    session.setAttribute("message", "同一目录不移动");
                    session.setAttribute("DirYd", null);
                    return "redirect:/mydoc/queryOne";
                }
                String path = dir.getPath() + "\\" + dir.getName();
                String path1 = dir1.getPath() + "\\" + dir1.getName();
                //path]" + path);
                String path0 = path1.replaceAll("\\\\", "\\\\\\\\\\\\\\\\");
                //path0]" + path0);


                //要保证这两个方法都能查到pid,uid
                //2.查询所有

                List<FileEntity> fileEntityList = fileService.selectAllFileByFatherFolder(path0, user, status);
                //文件】】" + fileEntity);
                List<Dir> dirList = dirService.selectAllFolderBy(path0, user, status);
                //dir】】" + dir2);
                //$dir1$" + dir1);
                dir1.setDid(dir.getId());
                //$dir1$" + dir1);
                dirList.add(dir1);

                //数据库操作
                //5.数据库复制信息
                StringBuilder pathFirst;
                //文件操作】】" + fileEntity);
                for (FileEntity fileEntity : fileEntityList) {
                    //FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
                    pathFirst = new StringBuilder(dir.getPath() + "\\" + dir.getName());
                    String[] ff = fileEntity.getPath().split("\\\\");
                    //ffs]" + s);

                    boolean judge = false;
                    for (String s : ff) {
                        //要移动文件夹的名字等于文件路径上的名字
                        //ff[i]】" + s);
                        //dir1.getName()】" + dir1.getName());
                        if (s.equals(dir1.getName())) {
                            //【进入到judge判断】 ");
                            judge = true;
                        }
                        if (judge) {
                            //judge]" + judge);
                            pathFirst.append("\\").append(s);
                        }
                    }
                    //新路径pathFirst]" + pathFirst);
                    //
                    fileService.deleteFileById(fileEntity.getId());
                    //处理完成，
                    //进入处理数据库环节");
                    //更新数据
                    //原来的file]" + fileEntity);
                    fileEntity.setPath(pathFirst.toString());
                    //新的的file]" + fileEntity);
                    fileService.addFile(fileEntity);
                    //更新文件成功");
                }
                //文件夹操作dir】】" + dir2);
                for (Dir dir2 : dirList) {
                    //DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
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
                    //新路径pathFirst]" + pathFirst);
                    //把原来的文件夹删除
                    dirService.deleteFolderById(dir2.getId());
                    //处理完成，
                    //进入处理数据库环节");
                    //更新数据
                    //原来的dir2]" + dir2);
                    dir2.setPath(pathFirst.toString());

                    //Dir dir3 = dir2;对象是引用数据类型的

                    //【新的的目录2】" + dir2);
                    dirService.addRootDir(dir2);

                    //最后
                    //更新成功");
                }
                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                //1.服务器移动
                TextCopyFileAndMove.copyDir(path, path1);
                //服务器移动完成");

                //删除原来的文件
                //进入到删除文件夹方法&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

                //找出所有的子目录///
                List<Dir> targetFolder = dirService.selectAllFolderByFatherFolder(path0, dir1.getId());
                //targetFolder]" + targetFolder);

                //删除子目录下的文件和文件夹
                for (Dir f : targetFolder) {
                    //【f】" + f);
                    fileService.deleteFileByFolderId(f.getId());
                    dirService.deleteFolderById(f.getId());
                }
               //最后删除目标文件夹
                dirService.deleteFolderById(id);
                File fulldir = new File(path1);
                //找出所有的子目录///
                //4.服务器执行删除实际文件夹
                DeleteAllByPath.deleteFile(fulldir);
                //【删除成功】");
                session.setAttribute("message", "文件夹移动成功");
            }
            session.setAttribute("DirYd", null);
        }
        return "redirect:/mydoc/queryOne";
    }


    @RequestMapping("inSearch")
    public String inSearch(String inSearchName, HttpSession session) {
        //inSearchName]" + inSearchName);
        User user = (User) session.getAttribute("user");
        int status = 1;
        //user]" + user);
        //status]" + status);
        List<Dir> dirs = dirService.queryAllByinSearchName(inSearchName, user, status);
        List<FileEntity> fileEntities = fileService.queryAllByinSearchName(inSearchName, user, status);

        //dirs]" + dirs);
        //files]" + fileEntities);
        session.setAttribute("dirs", dirs);
        //图片路径
        List<String> stringList = new ArrayList<>();
        for (FileEntity fileEntity : fileEntities) {
            String path = fileEntity.getPath() + "\\" + fileEntity.getNewName();
            stringList.add(path);
        }
        session.setAttribute("stringList", stringList);

        session.setAttribute("files", fileEntities);
        //查询完成");
        return "redirect:/jsp/front/main/detail/mydoc.jsp";
    }


    /*错误提示框，复制，重命名，分享*/
    @RequestMapping("changFileName")
    public String changFileName(String fileName, HttpSession session, String fileId) {
        //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
        //fileName]" + fileName);
        if (fileId != null) {
            FileEntity fileEntity = fileService.selectFileById(fileId);
            //s.split("\\.")
            session.setAttribute("text_name", fileEntity.getName().substring(0, fileEntity.getName().lastIndexOf(".")));
            session.setAttribute("changFileName", fileId);
        } else {
            if (fileName == null || fileName.equals("")) {
                //为空");
                session.setAttribute("message", "新文件名为空");
                session.setAttribute("text_name", null);
                session.setAttribute("changFileName", null);
            } else {
                //不为空");
                //查询
                fileId = (String) session.getAttribute("changFileName");
                FileEntity fileEntity = fileService.selectFileById(fileId);
                //file]" + fileEntity);
                String newName = fileName + fileEntity.getZFile();
                fileEntity.setName(newName);
                //file]" + fileEntity);
                User user = (User) session.getAttribute("user");
                int status = 1;
                //考虑user,status,name,path
                FileEntity filenew = fileService.queryByUserStatusNamePath(user, status, fileEntity);
                if (filenew == null) {
                    fileService.updateNameById(fileEntity);
                }
                fileEntity.setOptionTime(new Date());
                fileService.updateTime(fileEntity);
                session.setAttribute("message", "文件重命名成功");
            }
            session.setAttribute("text_name", null);
            session.setAttribute("changFileName", null);
        }
        return "redirect:/mydoc/queryOne";
    }


    @RequestMapping("changeDirName")
    public String changeDirName(String dirName, HttpSession session, String dirId) {
        //CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
        //dirName]" + dirName);
        if (dirId != null) {
            Dir dir = dirService.selectById(dirId);
            //s.split("\\.")
            session.setAttribute("text_name2", dir.getName());
            session.setAttribute("changeDirName", dirId);
        } else {
            if (dirName == null || dirName.equals("")) {
                session.setAttribute("message", "新文件夹名字为空");
                //为空");
                session.setAttribute("text_name2", null);
                session.setAttribute("changeDirName", null);
            } else {
                //不为空");
                //查询
                dirId = (String) session.getAttribute("changeDirName");
                Dir dir = dirService.selectById(dirId);
                //dir]" + dir);
                String path0 = dir.getPath() + "\\" + dir.getName();
                String path0_my = path0.replaceAll("\\\\", "\\\\\\\\\\\\\\\\");
                String[] dirPath = dir.getPath().split("\\\\");
                //dirPath]" + Arrays.toString(dirPath));
                int length = dirPath.length;
                //length]" + length);
                //服务器文件夹重命名
                //想命名的原文件夹的路径
                File file1 = new File(path0);
                //将原文件夹更改为A，其中路径是必要的。注意
                file1.renameTo(new File(dir.getPath() + "\\" + dirName));
                //服务器重命名成功");
                //
                dir.setName(dirName);
                //dir]" + dir);
                User user = (User) session.getAttribute("user");
                int status = 1;
                //考虑user,status,name,path
                Dir dir1 = dirService.queryByUserStatusNamePath(user, status, dir);
                dirService.updateNameById(dir);
                if (dir1 == null) {
                    //更新子文件夹和文件路径
                    List<FileEntity> fileEntityList = fileService.selectAllFileByFatherFolder(path0_my, user, status);
                    for (FileEntity fileEntity : fileEntityList) {
                        //文件】】" + fileEntity);
                        String[] pathfile = fileEntity.getPath().split("\\\\");
                        //pathfile]" + Arrays.toString(pathfile));
                        pathfile[length] = dirName;
                        StringBuilder newFilePath = new StringBuilder();
                        for (int i = 0; i < pathfile.length; i++) {
                            if (i != pathfile.length - 1) {
                                newFilePath.append(pathfile[i]).append("\\");
                            } else {
                                newFilePath.append(pathfile[i]);
                            }
                        }
                        //newFilePath]" + newFilePath);
                        fileEntity.setPath(newFilePath.toString());
                        fileService.updatePathById(fileEntity);
                    }
                    List<Dir> dirList = dirService.selectAllFolderBy(path0_my, user, status);

                    for (Dir dir2 : dirList) {
                        //dir】】" + dir2);
                        String[] pathDir = dir2.getPath().split("\\\\");
                        pathDir[length] = dirName;
                        StringBuilder newDirPath = new StringBuilder();
                        for (int i = 0; i < pathDir.length; i++) {
                            if (i != pathDir.length - 1) {
                                newDirPath.append(pathDir[i]).append("\\");
                            } else {
                                newDirPath.append(pathDir[i]);
                            }
                        }
                        //newDirPath]" + newDirPath);
                        dir2.setPath(newDirPath.toString());
                        dirService.updatePathById(dir2);
                    }
                    session.setAttribute("message", "文件夹重命名成功");
                }
                session.setAttribute("message", "文件夹重命名失败");
            }
            session.setAttribute("text_name2", null);
            session.setAttribute("changeDirName", null);
        }
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
        return "redirect:/mydoc/queryOne";
    }


    @RequestMapping("openBefore")
    public String openBefore(HttpSession session, String fileId) {
        FileEntity fileEntity = fileService.selectFileById(fileId);
        String did = fileEntity.getDir().getId();
        session.setAttribute("did", did);
        User user = (User) session.getAttribute("user");
        int status = 1;
        List<FileEntity> fileEntities = fileService.queryAllById(did, user, status);
        List<Dir> dirs = dirService.queryAllById(did, user, status);
        //dirs]" + dirs);
        //files]" + fileEntities);
        session.setAttribute("dirs", dirs);
        session.setAttribute("files", fileEntities);
        //查询完成");
        return "redirect:/jsp/front/main/detail/mydoc.jsp";
    }

    @RequestMapping("recorverFile")
    public String recorverFile(String fileId) {
        FileEntity fileEntity = fileService.selectFileById(fileId);
        fileEntity.setStatus(1);
        fileService.updateStatus(fileEntity);
        return "redirect:/mydoc/queryOne";
    }

    @RequestMapping("recorverDir")
    public String recorverDir(String pid) {

        Dir dir1 = dirService.selectById(pid);
        dir1.setStatus(1);
        dirService.updateStatue(dir1);
        return "redirect:/mydoc/queryOne";
    }
}
