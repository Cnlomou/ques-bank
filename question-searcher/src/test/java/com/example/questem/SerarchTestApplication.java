package com.example.questem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Linmo
 * @create 2020/5/14 20:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SerarchTestApplication {

    @Autowired
//    RabbitTemplate rabbitTemplate;

    @Test
    public void test(){
//        rabbitTemplate.convertAndSend("ques.bank","ques.update","hello world");
    }
}
