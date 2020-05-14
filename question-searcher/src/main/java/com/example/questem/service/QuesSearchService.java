package com.example.questem.service;

import com.example.questem.entity.Question;

/**
 * @author Linmo
 * @create 2020/5/14 19:19
 */
public interface QuesSearchService {

    Object searchByQuesTitle(String title);

    Question searchByQuesId(Long quesId);
}
