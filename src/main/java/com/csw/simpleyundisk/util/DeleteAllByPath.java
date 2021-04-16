package com.csw.simpleyundisk.util;

import java.io.File;

public class DeleteAllByPath {

    public static void deleteFile(File dir) {
        //1,获取该文件夹下的所有的文件和文件夹
        File[] subFiles = dir.listFiles();
        //2,遍历数组
        assert subFiles != null;
        for (File subFile : subFiles) {
            //3,判断是文件直接删除
            if (subFile.isFile()) {
                subFile.delete();
                //4,如果是文件夹,递归调用
            } else {
                deleteFile(subFile);
            }
        }
        //5,循环结束后,把空文件夹删掉
        dir.delete();
    }
}
