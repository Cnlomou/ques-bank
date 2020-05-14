package com.example.questem.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Linmo
 * @create 2020/5/13 22:00
 */
@Document(indexName = "quesbank",type = "normal")
public class Question {
    @Id
    Long id;
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    String title;
    @Field(type = FieldType.Text)
    String answer;
    @Field(type = FieldType.Integer)
    Integer visit;
    @Field(type = FieldType.Date)
    Date lastTime;

    List<QuestionOp> optionList;

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", answer='" + answer + '\'' +
                ", visit=" + visit +
                ", lastTime=" + lastTime +
                ", optionList=" + optionList +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getVisit() {
        return visit;
    }

    public void setVisit(Integer visit) {
        this.visit = visit;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public List<QuestionOp> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<QuestionOp> optionList) {
        this.optionList = optionList;
    }
}
