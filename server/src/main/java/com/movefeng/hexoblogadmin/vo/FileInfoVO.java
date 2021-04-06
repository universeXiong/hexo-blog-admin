package com.movefeng.hexoblogadmin.vo;

import lombok.Data;

@Data
public class FileInfoVO {
    
    private String url;

    private String fileName;

    /**
     * 文件大小，单位：字节
     */
    private long fsize;
    /**
     * 文件的mimeType
     */
    private String mimeType;
    /**
     * 文件的状态，0表示启用，1表示禁用
     */
    private int status;
}
