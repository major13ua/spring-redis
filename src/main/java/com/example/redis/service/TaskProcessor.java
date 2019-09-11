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

    private final DirectService directService;

    @Scheduled(fixedDelay = 1000)
    public void processTask() {
        Optional<QueryTaskDTO> item = directService.next();
        if (item.isPresent()) {
            log.info(item.get().toString());
        } else {
            try {
                log.info("No data sleep");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}
