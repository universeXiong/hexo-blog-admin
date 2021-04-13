package com.movefeng.hexoblogadmin.dao;

import com.github.pagehelper.Page;
import com.movefeng.hexoblogadmin.model.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Map;

/**
 * @Entity com.movefeng.hexoblogadmin.model.FileInfo
 */
@Mapper
public interface FileInfoDao {

    int insertBatch(@Param("fileInfoCollection") Collection<FileInfo> fileInfoCollection);

    int deleteAll();

    Page<FileInfo> queryFileList(@Param("param") Map<String, Object> searchParam);

}