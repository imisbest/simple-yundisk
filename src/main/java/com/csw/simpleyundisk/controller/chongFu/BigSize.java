package com.csw.simpleyundisk.controller.chongFu;

import com.csw.simpleyundisk.entity.FileEntity;
import com.csw.simpleyundisk.util.MD5Utils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;

public class BigSize {
    public static String bigSize(MultipartFile bb, String realPath, String newFileName, FileEntity fileEntity, String suffix) {
        double sizes = bb.getSize();
        String size = null;
        if (sizes == 0) {
            size = "1k";
        }
        double sizes1 = sizes / 1024;
        int coutDiv = 1;
        for (int m = 1; m <= 2; m++) {
            if (sizes1 > 1024) {
                coutDiv++;
                sizes1 = sizes1 / 1024;
            }
        }
        if (coutDiv == 1) {
            BigDecimal b = new BigDecimal(sizes / 1024);
            double sz = b.setScale(2, BigDecimal.ROUND_UP).doubleValue();
            size = sz + "K";
        } else if (coutDiv == 2) {
            BigDecimal b = new BigDecimal(sizes / 1024 / 1024);
            double sz = b.setScale(2, BigDecimal.ROUND_UP).doubleValue();
            size = sz + "M";
        } else if (coutDiv == 3) {
            BigDecimal b = new BigDecimal(sizes / 1024 / 1024);
            double sz = b.setScale(2, BigDecimal.ROUND_UP).doubleValue();
            size = sz + "G";
        }
        fileEntity.setSizes(size);
        fileEntity.setType(bb.getContentType());
        fileEntity.setCreateTime(new Date());
        fileEntity.setOptionTime(new Date());
        fileEntity.setCount(0);
        fileEntity.setStatus(1);
        String filePath = realPath + "\\" + newFileName;
        String checkMd5 = MD5Utils.getmd5One(filePath);
        fileEntity.setCheckMd5(checkMd5);
        fileEntity.setIsShare(0);
        String icon;
        switch (suffix) {
            case ".png":
            case ".jpeg":
            case ".jpg":
                icon = "图片";
                break;
            case ".zip":
            case ".rar":
            case ".7z":
                icon = "压缩包";
                break;
            case ".doc":
            case ".docx":
            case ".pdf":
            case ".txt":
                icon = "文档";
                break;
            default:
                icon = "其他";
                break;
        }
        fileEntity.setIcon(icon);
        return checkMd5;
    }

}
