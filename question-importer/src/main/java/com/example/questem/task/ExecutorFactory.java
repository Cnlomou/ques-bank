package com.example.questem.task;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Linmo
 * @create 2020/5/14 22:41
 */
public class ExecutorFactory {

    private static ExecutorService executorService=null;

    public static synchronized ExecutorService getInstance(){
        if(executorService==null)
            executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        return executorService;
    }
}
