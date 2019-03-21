package com.company.project.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTask {

    @Scheduled(cron = "0 0/1 * * * ?")
    public void myScheduledTask() {
        System.out.println("This is a test scheduled task for every minutes...");
    }
}
