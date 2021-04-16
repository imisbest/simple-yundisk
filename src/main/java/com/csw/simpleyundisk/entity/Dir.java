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
/*@ToString(exclude = "dir")*/
public class Dir implements Serializable {

    private String id;
    private String name;
    private String path;
    private long count;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private long status;
    private long isShare;
    private String icon;
    //    private String pid;
//    private String uid;
    private String did;
    //private Dir dir;
    private User user;


}
