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
public class User implements Serializable {

    private String id;
    private String name;
    private String email;
    private String pwd;
    private String phone;
    private String code;
    private String salt;
    private String status;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;
    private String hidePwd;
    private String hideSalt;

}
