package com.example.questem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author Linmo
 * @create 2020/5/14 19:12
 */

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.example.questem.repository")
//@EnableRabbit
public class SearcherApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearcherApplication.class,args);
    }
}
