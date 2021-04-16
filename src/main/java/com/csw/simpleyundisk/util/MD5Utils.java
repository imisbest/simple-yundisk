package com.csw.simpleyundisk.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MD5Utils {
    private final static String[] STR_HEX = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    //给定一个字符串
    static String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    //获取文件MD5
    public static String getmd5One(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(FileUtils.readFileToByteArray(new File(path)));
            for (int value : b) {
                int d = value;
                if (d < 0) {
                    d += 256;
                }
                int d1 = d / 16;
                int d2 = d % 16;
                sb.append(STR_HEX[d1]).append(STR_HEX[d2]);
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getSalt() {
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i <= 7; i++) {
            salt.append(str.charAt(new Random().nextInt(62)));
        }
        return salt.toString();
    }

    public static String getNum() {
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i <= 8; i++) {
            salt.append(str.charAt(new Random().nextInt(10)));
        }
        return salt.toString();
    }

    public static String getPassword(String upassword) {
        return DigestUtils.md5Hex(upassword);
    }
}

