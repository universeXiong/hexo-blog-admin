package com.movefeng.hexoblogadmin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movefeng.hexoblogadmin.model.User;
import com.movefeng.hexoblogadmin.vo.FileInfoVO;
import com.movefeng.hexoblogadmin.vo.Result;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.UrlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/images/list")
    public Result list() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释

        String accessKey = "";
        String secretKey = "";
        String domainOfBucket = "";

        String bucket = "image-for-hexo";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);

        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";

        //列举空间文件列表
        List<FileInfoVO> fileInfoList = new ArrayList<>();
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                // System.out.println(item.key);
                // System.out.println(item.hash);
                // System.out.println(item.fsize);
                // System.out.println(item.mimeType);
                // System.out.println(item.putTime);
                // System.out.println(item.endUser);

                String finalUrl = String.format("%s/%s", domainOfBucket, UrlUtils.urlEncode(item.key));
                System.out.println(finalUrl);

                FileInfoVO fileInfoVO = new FileInfoVO();
                BeanUtils.copyProperties(item, fileInfoVO);
                fileInfoVO.setFileName(item.key);
                fileInfoVO.setUrl(finalUrl);
                fileInfoList.add(fileInfoVO);
            }
        }

        PageInfo<FileInfoVO> pageInfo = new PageInfo<>(fileInfoList);
        return new Result<>(Result.Code.SUCCESS, pageInfo);
    }
}
