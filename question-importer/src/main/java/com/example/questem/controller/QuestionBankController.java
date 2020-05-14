package com.example.questem.controller;

import com.example.questem.entity.Result;
import com.example.questem.entity.ResultCode;
import com.example.questem.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Linmo
 * @create 2020/5/13 20:10
 */
@RestController
@CrossOrigin
@RequestMapping("/ques")
public class QuestionBankController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/upload")
    public Result importQuesFormFile(@RequestParam(name = "file") MultipartFile[] multipartFile){
        questionService.questionImport(multipartFile);
        return new Result(true, ResultCode.SUCCESS,"导入成功");
    }
}
