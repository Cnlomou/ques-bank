package com.example.questem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author Linmo
 * @create 2020/5/13 21:56
 */
@SpringBootApplication
@MapperScan(basePackages = "com.example.questem.dao")
@EnableElasticsearchRepositories(basePackages = "com.example.questem.repository")
@EnableScheduling
public class EsManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EsManagerApplication.class,args);
    }
}
