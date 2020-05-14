package com.example.questem.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Linmo
 * @create 2020/5/13 19:20
 */
@Table(name = "tb_ques_options")
public class QuestionOption {
    @Id
    private Long id;
    @Column(name = "options")
    private String options;
    @Column(name = "label")
    private String label;
    @Column(name = "ques_id")
    private Long quesId;

    @Override
    public String toString() {
        return "QuestionOption{" +
                "id=" + id +
                ", options='" + options + '\'' +
                ", label='" + label + '\'' +
                ", quesId=" + quesId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getQuesId() {
        return quesId;
    }

    public void setQuesId(Long quesId) {
        this.quesId = quesId;
    }
}
