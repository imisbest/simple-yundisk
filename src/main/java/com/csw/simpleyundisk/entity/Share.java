package com.csw.simpleyundisk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class Share {

    private String id;
    private String fromId;
    private String toId;
    private FileEntity fileEntity;
    private Dir dir;


}
