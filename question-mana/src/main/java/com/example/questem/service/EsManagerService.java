package com.example.questem.service;

/**
 * @author Linmo
 * @create 2020/5/13 22:53
 */
public interface EsManagerService {
    void questionsImport();
    void questionsImport(Integer size,Boolean isNew);
    void questionsExport();
    void questionsExport(Integer size);
}
