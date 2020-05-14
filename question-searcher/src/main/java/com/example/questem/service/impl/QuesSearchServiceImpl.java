package com.example.questem.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.questem.Constant;
import com.example.questem.entity.Question;
import com.example.questem.repository.QuestionRepository;
import com.example.questem.service.QuesSearchService;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Linmo
 * @create 2020/5/14 19:21
 */
@Service
public class QuesSearchServiceImpl implements QuesSearchService {
    @Autowired
    private QuestionRepository questionRepository;

    private final Logger logger = LoggerFactory.getLogger(QuesSearchServiceImpl.class);

    private ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    @Override
    @Nullable
    public Object searchByQuesTitle(String title) {
        if (StringUtils.isEmpty(title)) return null;
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(title).field("title"))
                .withPageable(PageRequest.of(0, 5))
                .build();

        return queryQues(query, title);
    }

    private Object queryQues(NativeSearchQuery nativeSearchQuery, String title) {
        Question question = null;
        if ((question = queryQuesIfExist(title)) != null) {
           updateQuestion(question);
            setCache(question, title);
            return question;
        }
        Page<Question> search = questionRepository.search(nativeSearchQuery);
        List<Question> content = search.getContent();
        if (content.size() > 0) {
            question = content.get(0);
            updateQuestion(question);
//            sendMessage(question, title);
            setCache(question, title);
            recodeNewKey(title);
            return question;
        }
        if (logger.isWarnEnabled())
            logger.warn("没有查询到的题目：" + title);
        throw new IllegalStateException("抱歉，题库中没有该题，等待你的补充哦~~~");
    }

    /**
     * 查询成功后的更新
     * @param question
     */
    private void updateQuestion(Question question) {
        question.setVisit(question.getVisit() + 1);
        question.setLastTime(new Date());
    }

    private void recodeNewKey(String title) {
        service.submit(() -> {
            try {
                BoundListOperations boundListOperations = redisTemplate.boundListOps(Constant.QUES_NEW_KEY);
                boundListOperations.leftPush(title);
                if (logger.isInfoEnabled())
                    logger.info("记录ques新键:" + title);
            } catch (Exception e) {
                if (logger.isErrorEnabled())
                    logger.error("记录ques新键出错:" + e.getMessage());
            }

        });
    }

    /**
     * 发送修改消息
     *
     * @param question
     * @param title
     */
//    private void sendMessage(Question question, String title) {
//        service.submit(() -> {
//            try {
//                rabbitTemplate.convertAndSend(Constant.EXCHANGE, Constant.ROUTE_KEY,
//                        JSON.toJSONString(new HashMap.SimpleEntry<>(title, question)));
//                if (logger.isInfoEnabled())
//                    logger.info("发送成功question修改信息:" + title);
//            } catch (Exception e) {
//                if (logger.isErrorEnabled())
//                    logger.error("发送question update message 出错:" + e.getMessage());
//            }
//
//        });
//    }

    /**
     * 从缓存中查
     *
     * @param title
     * @return
     */
    private Question queryQuesIfExist(String title) {
        try {
            BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(Constant.QUES_PRE + title);
            return (Question) boundValueOperations.get();
        } catch (Exception e) {
            logger.error("从redis读出question出错:" + e.getMessage());
        }
        return null;
    }

    /**
     * 设置缓存
     *
     * @param question
     * @param key
     */
    private void setCache(Question question, Object key) {
        service.submit(() -> {
            try {
                BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(Constant.QUES_PRE + key);
                boundValueOperations.set(question);
                if (logger.isInfoEnabled())
                    logger.info("设置question cache:" + key);
            } catch (Exception e) {
                if (logger.isErrorEnabled())
                    logger.error(e.getMessage());
            }
        });
    }

    @Override
    public Question searchByQuesId(Long quesId) {
        return null;
    }
}
