package com.example.questem.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Linmo
 * @create 2020/5/13 19:24
 */

public interface QuestionService {
    void questionImport(MultipartFile[] multipartFile);
}
