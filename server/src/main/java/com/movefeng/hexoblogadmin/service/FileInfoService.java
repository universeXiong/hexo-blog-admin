package com.movefeng.hexoblogadmin.service;

import com.github.pagehelper.Page;
import com.movefeng.hexoblogadmin.dao.FileInfoDao;
import com.movefeng.hexoblogadmin.model.FileInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @Description:
 * @CreateTime:
 */
@Service
public class FileInfoService {
    @Resource
    private FileInfoDao fileInfoDao;

    @Transactional(rollbackFor = Exception.class)
    public void saveBatchWithRemove(List<FileInfo> list) {
        fileInfoDao.deleteAll();
        fileInfoDao.insertBatch(list);
    }

    public Page<FileInfo> queryFile(Map<String, Object> searchParam) {
        return fileInfoDao.queryFileList(searchParam);
    }
}
