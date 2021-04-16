package com.csw.simpleyundisk.util;

import java.io.*;

//实现文件的简单处理,复制和移动文件、目录等
public class TextCopyFileAndMove {
    //移动一个文件夹 里的全部文件到另一个文件夹中
    public static void fileMove(String from, String to) throws Exception {// 移动指定文件夹内的全部文件
        try {
            File dir = new File(from);
            File[] files = dir.listFiles();// 将文件或文件夹放入文件集
            if (files == null)// 判断文件集是否为空
            {
                return;
            }
            File moveDir = new File(to);// 创建目标目录
            if (!moveDir.exists()) {// 判断目标目录是否存在
                moveDir.mkdirs();// 不存在则创建
            }
            for (int i = 0; i < files.length; i++) {// 遍历文件集
                if (files[i].isDirectory()) {// 如果是文件夹或目录,则递归调用fileMove方法，直到获得目录下的文件
                    fileMove(files[i].getPath(), to + "\\" + files[i].getName());// 递归移动文件
                    files[i].delete();// 删除文件所在原目录
                }
                File moveFile = new File(moveDir.getPath() + "\\"// 将文件目录放入移动后的目录
                        + files[i].getName());
                if (moveFile.exists()) {// 目标文件夹下存在的话，删除
                    moveFile.delete();
                }
                files[i].renameTo(moveFile);// 移动文件
                //ln(files[i] + " 移动成功");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // 复制目录下的文件（不包括该目录）到指定目录，会连同子目录一起复制过去。
    public static void copyFileFromDir(String toPath, String fromPath) {
        File file = new File(fromPath);
        createFile(toPath, false);// true:创建文件 false创建目录
        if (file.isDirectory()) {// 如果是目录
            copyFileToDir(toPath, listFile(file));
        }
    }

    // 复制目录到指定目录,将目录以及目录下的文件和子目录全部复制到目标目录
    //**************************************************************
    public static void copyDir(String toPath, String fromPath) {
        File targetFile = new File(toPath);// 创建文件
        createFile(targetFile, false);// 创建目录
        File file = new File(fromPath);// 创建文件
        if (targetFile.isDirectory() && file.isDirectory()) {// 如果传入是目录
            copyFileToDir(targetFile.getAbsolutePath() + "/" + file.getName(),
                    listFile(file));// 复制文件到指定目录
        }
    }

    // 复制一组文件到指定目录。targetDir是目标目录，filePath是需要复制的文件路径
    //被调用
    public static void copyFileToDir(String toDir, String[] filePath) {
        if (toDir == null || "".equals(toDir)) {// 目录路径为空
            //参数错误，目标路径不能为空");
            return;
        }
        File targetFile = new File(toDir);
        if (!targetFile.exists()) {// 如果指定目录不存在
            targetFile.mkdir();// 新建目录
        } else {
            if (!targetFile.isDirectory()) {// 如果不是目录
                //参数错误，目标路径指向的不是一个目录！");
                return;
            }
        }
        for (int i = 0; i < filePath.length; i++) {// 遍历需要复制的文件路径
            File file = new File(filePath[i]);// 创建文件
            if (file.isDirectory()) {// 判断是否是目录
                copyFileToDir(toDir + "/" + file.getName(), listFile(file));// 递归调用方法获得目录下的文件
                //复制文件 " + file);
            } else {
                copyFileToDir(toDir, file, "");// 复制文件到指定目录
            }
        }
    }

    //****************************************************************
    //被调用
    public static void copyFileToDir(String toDir, File file, String newName) {// 复制文件到指定目录
        String newFile = "";
        if (newName != null && !"".equals(newName)) {
            newFile = toDir + "/" + newName;
        } else {
            newFile = toDir + "/" + file.getName();
        }
        File tFile = new File(newFile);
        copyFile(tFile, file);// 调用方法复制文件
    }

    //被调用
    public static void copyFile(File toFile, File fromFile) {// 复制文件
        if (toFile.exists()) {// 判断目标目录中文件是否存在
            //文件" + toFile.getAbsolutePath() + "已经存在，跳过该文件！");
            return;
        } else {
            createFile(toFile, true);// 创建文件
        }
        //复制文件" + fromFile.getAbsolutePath() + "到"
              //  + toFile.getAbsolutePath());
        try {
            InputStream is = new FileInputStream(fromFile);// 创建文件输入流
            FileOutputStream fos = new FileOutputStream(toFile);// 文件输出流
            byte[] buffer = new byte[1024];// 字节数组
            while (is.read(buffer) != -1) {// 将文件内容写到文件中
                fos.write(buffer);
            }
            is.close();// 输入流关闭
            fos.close();// 输出流关闭
        } catch (FileNotFoundException e) {// 捕获文件不存在异常
            e.printStackTrace();
        } catch (IOException e) {// 捕获异常
            e.printStackTrace();
        }
    }

    //被调用
    public static String[] listFile(File dir) {// 获取文件绝对路径
        String absolutPath = dir.getAbsolutePath();// 声获字符串赋值为路传入文件的路径
        String[] paths = dir.list();// 文件名数组
        String[] files = new String[paths.length];// 声明字符串数组，长度为传入文件的个数
        for (int i = 0; i < paths.length; i++) {// 遍历显示文件绝对路径
            files[i] = absolutPath + "/" + paths[i];
        }
        return files;
    }

    //被调用
    public static void createFile(String path, boolean isFile) {// 创建文件或目录
        createFile(new File(path), isFile);// 调用方法创建新文件或目录
    }

    //被调用
    public static void createFile(File file, boolean isFile) {// 创建文件
        if (!file.exists()) {// 如果文件不存在
            if (!file.getParentFile().exists()) {// 如果文件父目录不存在
                createFile(file.getParentFile(), false);
            } else {// 存在文件父目录
                if (isFile) {// 创建文件
                    try {
                        file.createNewFile();// 创建新文件
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    file.mkdir();// 创建目录
                }
            }
        }
    }
}
