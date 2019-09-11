package com.example.redis.service;

import com.example.redis.model.QueryTaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class MegaTaskExecutor {

    private final ExecutorService executor;

    public MegaTaskExecutor() {
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void execute(QueryTaskDTO qtDto) {
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            log.info("{} - Get - {}", threadName, qtDto);
            try {
                Thread.sleep(1000);
                log.info("{} - Processed - {}", threadName, qtDto);
            } catch (InterruptedException e) {
                log.error(threadName + " - " + e.getMessage(), e);
            }
        });
    }

}
