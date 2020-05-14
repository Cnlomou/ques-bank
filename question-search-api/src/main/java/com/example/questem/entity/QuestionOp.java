package com.example.questem.entity;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * @author Linmo
 * @create 2020/5/13 22:02
 */
public class QuestionOp {
   private String key;
   private String val;

    public QuestionOp() {
    }

    public QuestionOp(String key, String val) {
        this.key = key;
        this.val = val;
    }

    @Override
    public String toString() {
        return "QuestionOp{" +
                "key='" + key + '\'' +
                ", val='" + val + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
