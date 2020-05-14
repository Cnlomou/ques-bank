package com.example.questem.task;

import com.example.questem.service.EsManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Linmo
 * @create 2020/5/14 9:18
 */
@Component
public class DataStreamTask {
    private static final Double val = 0.8;
    @Autowired
    private EsManagerService esManagerService;

    @Scheduled(cron = "0 0 0/3 2,3,4,5 * ?")
    public void dataImport() {
        int oldVal = (int) (val * 100);
        int newVal = (int) ((1 - val) * 100);
        esManagerService.questionsImport(oldVal, false);
        esManagerService.questionsImport(newVal, true);
    }

    @Scheduled(cron = "0 0 0/3 2,3,4,5 * ?")
    public void dataExport() {
        esManagerService.questionsExport(100);
    }
}
