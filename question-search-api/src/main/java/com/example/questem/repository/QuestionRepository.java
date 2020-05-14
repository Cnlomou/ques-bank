package com.example.questem.repository;

import com.example.questem.entity.Question;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Linmo
 * @create 2020/5/13 22:13
 */
public interface QuestionRepository extends ElasticsearchRepository<Question,Long> {
}
