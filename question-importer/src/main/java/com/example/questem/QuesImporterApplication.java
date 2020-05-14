package com.example.questem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Linmo
 * @create 2020/5/13 19:07
 */
@SpringBootApplication(exclude = ElasticsearchAutoConfiguration.class)
@MapperScan(basePackages = "com.example.questem.dao")
@EnableAsync
@EnableScheduling
public class QuesImporterApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuesImporterApplication.class,args);
    }
}
