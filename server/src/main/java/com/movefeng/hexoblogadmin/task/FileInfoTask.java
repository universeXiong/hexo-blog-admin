package com.movefeng.hexoblogadmin.task;

import com.movefeng.hexoblogadmin.service.FileInfoService;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.UrlUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @Description:
 * @CreateTime:
 */

@Data
@Slf4j
@Configuration
@EnableScheduling
@ConfigurationProperties(prefix = "qiniu")
public class FileInfoTask {
    private String accessKey;
    private String secretKey;
    private String domainOfBucket;
    private String bucket;

    @Resource
    private FileInfoService fileInfoService;

    // 每天凌晨1点执行一次
    @Scheduled(cron = "0 0 1 * * ?")
    // 每分钟执行一次
    // @Scheduled(cron = "0 */1 * * * ?")
    public void syncFileInfoTask() {
        log.info("执行定时任务时间: " + LocalDateTime.now());

        //构造一个带指定 Region 对象的配置类
        com.qiniu.storage.Configuration cfg = new com.qiniu.storage.Configuration(Region.region0());
        //...其他参数参考类注释

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);

        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";

        //列举空间文件列表
        List<com.movefeng.hexoblogadmin.model.FileInfo> fileInfoList = new ArrayList<>();
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                String finalUrl = String.format("%s/%s", domainOfBucket, UrlUtils.urlEncode(item.key));
                com.movefeng.hexoblogadmin.model.FileInfo fileInfo = new com.movefeng.hexoblogadmin.model.FileInfo();
                fileInfo.setName(item.key);
                fileInfo.setSize((int) item.fsize);
                fileInfo.setSuffix(item.mimeType);
                fileInfo.setUrl(finalUrl);
                fileInfo.setCreateDate(new Date(item.putTime / 10000));
                fileInfoList.add(fileInfo);
            }
        }
        fileInfoService.saveBatchWithRemove(fileInfoList);
        log.info("定时任务执行完成");
    }
}
