package com.example.redis.service;

import com.example.redis.model.QueryTaskDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TaskProcessor {

    private static final int SLEEP_DELAY = 10000;

    private final DirectService directService;
    private final MegaTaskExecutor taskExecutor;

    @Scheduled(fixedDelay = 100)
    public void processTask() {
        Optional<QueryTaskDTO> item = directService.next();
        if (item.isPresent()) {
            QueryTaskDTO qtDto = item.get();
            taskExecutor.execute(qtDto);
        } else {
            try {
                String threadName = Thread.currentThread().getName();
                log.info("{} - No data sleep for SLEEP_DELAY={}", threadName, SLEEP_DELAY);
                Thread.sleep(SLEEP_DELAY);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}
