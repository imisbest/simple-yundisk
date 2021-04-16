package com.csw.simpleyundisk.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class FileEntity implements Serializable {

    private String id;
    private String name;
    private String newName;
    private String zFile;
    private String path;
    private String sizes;
    private String type;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date optionTime;
    private long count;
    private long status;
    private String checkMd5;
    private long isShare;
    private String icon;
//    private String pid;
//    private String uid;

    private Dir dir;
    private User user;


}
