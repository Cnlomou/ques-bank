package com.example.questem.controller;

import com.example.questem.entity.Result;
import com.example.questem.entity.ResultCode;
import com.example.questem.service.EsManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Linmo
 * @create 2020/5/13 23:53
 */
@RestController
@CrossOrigin
public class EsImportController {

    @Autowired
    private EsManagerService esManagerService;

    @PutMapping("/import")
    public Result importData() {
        esManagerService.questionsImport();
        return new Result(true, ResultCode.SUCCESS, "导入成功");
    }
    @PutMapping("/import/size/{count}")
    public Result importDataBySize(@PathVariable(name = "count")Integer count,@RequestParam Boolean isNew){
        if(count>0){
            esManagerService.questionsImport(count,isNew);
            return new Result(true,ResultCode.SUCCESS,"导入成功");
        }
        return new Result(false,ResultCode.ERROR,"导入数据失败，count:"+count);
    }
}
