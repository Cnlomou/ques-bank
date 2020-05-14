package com.example.questem.task;

import com.example.questem.Constant;
import com.example.questem.dao.QuestionTemMapper;
import com.example.questem.entity.Question;
import com.example.questem.entity.QuestionTem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * @author Linmo
 * @create 2020/5/14 22:33
 */
@Component
public class QuesUpdateTask {
    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(QuesUpdateTask.class);
    @Resource
    private QuestionTemMapper questionTemMapper;

    @Scheduled(cron = "0/3 0 0,1,2 0,1,3,5 * ?")
//    @Scheduled(cron = "0/20 * * * * ?")
    public void updateData() {
        BoundListOperations boundListOperations = redisTemplate.boundListOps(Constant.QUES_NEW_KEY);
        Object title = boundListOperations.rightPop();
        if (title == null) return;
        Question question = (Question) redisTemplate.boundValueOps(Constant.QUES_PRE + title).get();

        QuestionTem questionTem = new QuestionTem();
        questionTem.setId(question.getId());
        questionTem.setVisit(question.getVisit());
        questionTem.setLastTime(question.getLastTime());
        ExecutorService instance = ExecutorFactory.getInstance();
        instance.submit(() -> {
            try {
                questionTemMapper.updateByPrimaryKeySelective(questionTem);
                redisTemplate.delete(Constant.QUES_PRE + title);
            } catch (Exception e) {
                logger.error("更新题库出错:" + e.getMessage());
            }
        });
        if (logger.isInfoEnabled())
            logger.info("向数据库中刷新question");
    }
}
