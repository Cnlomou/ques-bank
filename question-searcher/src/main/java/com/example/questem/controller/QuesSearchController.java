package com.example.questem.controller;

import com.example.questem.entity.Result;
import com.example.questem.entity.ResultCode;
import com.example.questem.service.QuesSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;

/**
 * @author Linmo
 * @create 2020/5/14 20:42
 */

@RestController
@RequestMapping("/search")
@CrossOrigin
public class QuesSearchController {

    @Autowired
    private QuesSearchService quesSearchService;

    @GetMapping("/title")
    public Result<Object> searchQues(String title) {
        Object o = quesSearchService.searchByQuesTitle(title);
        Assert.notNull(o, "请输入有效的题干");
        return new Result<>(true, ResultCode.SUCCESS, "SUCCESS", o);
    }
}
