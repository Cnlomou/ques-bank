package com.example.questem.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Linmo
 * @create 2020/5/13 19:17
 */

@Table(name = "tb_ques_tem")
public class QuestionTem {
    @Id
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "answer")
    private String answer;
    @Column(name = "visit")
    private Integer visit;
    @Column(name = "lastTime")
    private Date lastTime;

    @Override
    public String toString() {
        return "QuestionTem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", answer='" + answer + '\'' +
                ", visit='" + visit + '\'' +
                ", lastTime=" + lastTime +
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
}
