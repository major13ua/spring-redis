package com.example.redis.service;

import com.example.redis.model.QueryTask;
import com.example.redis.model.QueryTaskDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class DirectService {

    private static final String TASK_NAME = "tasks";

    private final RedisTemplate redisTemplate;

    public QueryTaskDTO addItem(QueryTaskDTO task) throws JsonProcessingException {
        QueryTask t = QueryTask.fromDTO(task);
        if (redisTemplate.hasKey(t.getId())) {
            log.info("Already exist key={}", t.getId());
            return QueryTaskDTO.fromTask(t);
        }
        ObjectMapper mapper = new ObjectMapper();
        final String tJson = mapper.writeValueAsString(t);
        long id = redisTemplate.opsForList().leftPush(TASK_NAME, tJson);
        log.info("Added id={}", id);
        return QueryTaskDTO.fromTask(t);
    }

    public Long size() {
        return redisTemplate.opsForList().size(TASK_NAME);
    }

    public Optional<QueryTaskDTO> next() {
        return Optional.ofNullable(redisTemplate.opsForList().rightPop(TASK_NAME))
                .map(value -> (String)value)
                .map(value -> toTask(value))
                .map(QueryTaskDTO::fromTask);
    }

    @SneakyThrows
    private QueryTask toTask(String value) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(value, QueryTask.class);
    }

}
