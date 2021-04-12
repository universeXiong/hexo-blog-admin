package com.movefeng.hexoblogadmin.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName file_info
 */
@Data
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 业务id
     */
    private Long businessId;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 访问地址
     */
    private String url;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 上传来源
     */
    private String uploadAgent;

    /**
     * 上传IP
     */
    private String uploadIp;

    /**
     * 排序
     */
    private Integer displayOrder;

    /**
     * 备注
     */
    private String note;

    /**
     * 删除
     */
    private Integer del;

    /**
     * 创建者
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新者
     */
    private Long updater;

    /**
     * 更新时间
     */
    private Date updateDate;
}