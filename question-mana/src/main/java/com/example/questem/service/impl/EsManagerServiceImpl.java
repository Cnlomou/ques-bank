package com.example.questem.service.impl;

import com.example.questem.dao.QuestionOptionMapper;
import com.example.questem.dao.QuestionTemMapper;
import com.example.questem.entity.Question;
import com.example.questem.entity.QuestionOp;
import com.example.questem.entity.QuestionOption;
import com.example.questem.entity.QuestionTem;
import com.example.questem.repository.QuestionRepository;
import com.example.questem.service.EsManagerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Linmo
 * @create 2020/5/13 22:56
 */
@Service
public class EsManagerServiceImpl implements EsManagerService {
    Logger logger = LoggerFactory.getLogger(EsManagerService.class);
    @Autowired
    private QuestionRepository questionRepository;
    @Resource
    private QuestionTemMapper questionTemMapper;
    @Resource
    private QuestionOptionMapper questionOptionMapper;

    @Override
    public void questionsImport() {
        logger.info("开始导入数据-------");
        long l = System.currentTimeMillis();
        Example lastTime = Example.builder(QuestionTem.class)
                .orderByDesc("lastTime")
                .build();
        PageHelper.startPage(1, 30);
        List<QuestionTem> questionTems = questionTemMapper.selectByExample(lastTime);
        resolveAndImport(questionTems);
        PageInfo<QuestionTem> questionTemPageInfo = new PageInfo<>(questionTems);
        int pages = questionTemPageInfo.getPages();
        for (int i = 2; i <= pages; i++) {
            PageHelper.startPage(i, 30);
            questionTems = questionTemMapper.selectByExample(lastTime);
            resolveAndImport(questionTems);
        }
        logger.info("导入数据完成耗时{}ms", System.currentTimeMillis() - l);
    }

    @Override
    public void questionsImport(Integer size, Boolean isNew) {
        if (size <= 0) return;
        Example.Builder lastTime1 = Example.builder(QuestionTem.class)
                .orderByDesc("lastTime");
        if (isNew) {
            lastTime1.orderByAsc("visit");
        }
        Example build = lastTime1.build();
        //导入数据
        List<QuestionTem> questionTems = null;
        int pageNum = 1;
        int count = size;
        long l = System.currentTimeMillis();
        while (count > 0) {
            PageHelper.startPage(pageNum++, count > 40 ? 40 : count);
            questionTems = questionTemMapper.selectByExample(build);
            if (questionTems.size() == 0) break;
            count -= questionTems.size();
            resolveAndImport(questionTems);
        }
        logger.info("导入{}数据完成耗时{}ms", new Object[]{size - count, System.currentTimeMillis() - l});
    }

    private void resolveAndImport(List<QuestionTem> questionTems) {
        List<Question> questions = new ArrayList<>();
        for (QuestionTem questionTem : questionTems) {
            Question question = new Question();
            questions.add(question);

            question.setId(questionTem.getId());
            question.setAnswer(questionTem.getAnswer());
            question.setLastTime(questionTem.getLastTime());
            question.setTitle(questionTem.getTitle());
            question.setVisit(questionTem.getVisit());
            List<QuestionOp> optionList = new ArrayList<>();
            question.setOptionList(optionList);
            getQuestionOption(optionList, questionTem);
        }
        questionRepository.saveAll(questions);
    }

    private void getQuestionOption( List<QuestionOp> optionList, QuestionTem questionTem) {
        QuestionOption questionOption1 = new QuestionOption();
        questionOption1.setQuesId(questionTem.getId());
        List<QuestionOption> select = questionOptionMapper.select(questionOption1);
        for (QuestionOption questionOption : select) {
            optionList.add(new QuestionOp(questionOption.getLabel(),questionOption.getOptions()));
        }
    }

    @Override
    public void questionsExport() {

    }

    @Override
    public void questionsExport(Integer size) {
        if (size <= 0) return;
        int count = size;
        long l = System.currentTimeMillis();
        long getSize = -1;
        while (count > 0) {
            NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("visit").order(SortOrder.ASC));
            nativeSearchQueryBuilder.withPageable(PageRequest.of(0, count > 100 ? 100 : count));
            NativeSearchQuery build = nativeSearchQueryBuilder.build();
            Page<Question> search = questionRepository.search(build);
            getSize = search.getContent().size();
            if (getSize == 0) break;
            count -= getSize;
            deleteData(search.getContent());
        }
        logger.info("es 删除{}数据，耗时{}ms", new Object[]{size - count, System.currentTimeMillis() - l});
    }

    private void deleteData(List<Question> content) {
        questionRepository.deleteAll(content);
    }
}
