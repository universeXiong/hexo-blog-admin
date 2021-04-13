package com.movefeng.hexoblogadmin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movefeng.hexoblogadmin.model.FileInfo;
import com.movefeng.hexoblogadmin.service.FileInfoService;
import com.movefeng.hexoblogadmin.vo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileInfoService fileInfoService;

    @RequestMapping("/images/list")
    public Result list(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestBody(required = false) Map<String, Object> searchParam) {
        PageHelper.startPage(pageNum, pageSize);
        if (searchParam == null) {
            searchParam = new HashMap<>();
        }
        Page<FileInfo> page = fileInfoService.queryFile(searchParam);
        PageInfo<FileInfo> pageInfo = new PageInfo<>(page);
        return new Result<>(Result.Code.SUCCESS, pageInfo);
    }
}
