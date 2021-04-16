package com.csw.simpleyundisk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class Friend implements Serializable {
    private String id;
    private User user;
    private User fuser;
}
