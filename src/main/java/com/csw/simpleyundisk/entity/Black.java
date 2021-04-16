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
public class Black implements Serializable {

    private String uid;
    private long count;

}
