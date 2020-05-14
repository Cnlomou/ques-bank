package com.example.questem.listenner;

import com.alibaba.fastjson.JSON;
import com.example.questem.Constant;
import com.example.questem.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Linmo
 * @create 2020/5/14 21:56
 */
//@Component
public class QuesUpdateInfoListener {

    @Autowired
    RedisTemplate redisTemplate;

//    @RabbitListener(queues = "ques.info")
    public void updateInfoHandler(String message){
        Map map = JSON.parseObject(message, Map.class);
//        map.forEach((k,v)->{
//            try {
//                BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(Constant.QUES_PRE + k);
//                Question question = (Question) boundValueOperations.get();
//            }catch (Exception e){
//
//            }
//        });
    }
}
