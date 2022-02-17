package com.example.s13firstspring.services;

import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public class CronJobs {
    @Async
    @Scheduled(fixedRate = 1000)
    @SneakyThrows
    public void cronJob(){
        Thread.sleep(1000*60*60);

    }
}
