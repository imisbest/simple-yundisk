package com.csw.simpleyundisk;

import com.csw.simpleyundisk.entity.Dir;
import com.csw.simpleyundisk.entity.FileEntity;
import com.csw.simpleyundisk.entity.Friend;
import com.csw.simpleyundisk.service.DirService;
import com.csw.simpleyundisk.service.FileService;
import com.csw.simpleyundisk.service.FriendService;
import com.csw.simpleyundisk.service.ShareService;
import com.csw.simpleyundisk.util.TextCopyFileAndMove;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class SimpleYundiskApplicationTests {
    @Autowired
    public ShareService shareService;
    @Autowired
    private FileService fileService;
    @Autowired
    private DirService dirService;
    @Autowired
    private FriendService friendService;

    @Test
    public void testFile() {
        FileEntity fileEntity = fileService.selectFileById("03191399-be86-4e9e-85e9-ceeab065ab7d");
        //ln(fileEntity);
    }

    @Test
    public void testFriend() {
        List<Friend> friendList = friendService.selectByUid("8a0c239d-8463-43ec-ac48-39fbba7964d4");
        //friendList]" + friendList);
    }

    @Test
    public void testqueryAllfolder() {
        Dir dir = dirService.selectById("2e845139-8c7e-4a25-a34c-4efcad7c4da6");
        String path = dir.getPath() + "\\" + dir.getName();
        //path]" + path);
        String path0 = path.replaceAll("\\\\", "\\\\\\\\\\\\\\\\");
        //path0]" + path0);
        //找出所有的子目录///
        List<Dir> targetFolder = dirService.selectAllFolderByFatherFolder(path0, dir.getId());
        //【targetFolder】" + targetFolder);
    }

    @Test
    public void copyDir() {
        TextCopyFileAndMove.copyDir(
                "C:\\Users\\Administrator\\Desktop\\一级文件夹\\二级文件夹移动过来",
                "C:\\Users\\Administrator\\Desktop\\一级文件夹\\二级文件夹");

    }

    @Test
    public void copyFileToDir() {
        TextCopyFileAndMove.copyFileToDir("C:\\Users\\Administrator\\Desktop\\一级文件夹\\1-1.txt移动过来",
                new File("C:\\Users\\Administrator\\Desktop\\一级文件夹\\1-1.txt"
                ), "1-1.txt");
    }
}
