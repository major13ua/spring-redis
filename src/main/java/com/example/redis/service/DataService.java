package com.example.redis.service;

import com.example.redis.model.QueryTask;
import com.example.redis.model.QueryTaskDTO;
import com.example.redis.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
@Slf4j
public class DataService {

    private final TaskRepository taskRepository;

    public QueryTaskDTO addTask(@Valid QueryTaskDTO taskDTO) {
        QueryTask t = QueryTask.fromDTO(taskDTO);
        if (taskRepository.existsById(t.getId())) {
            return QueryTaskDTO.fromTask(t);
        }
        return QueryTaskDTO.fromTask(taskRepository.save(t));
    }

    public List<QueryTaskDTO> getAll() {
        return StreamSupport.stream(taskRepository.findAll().spliterator(), false)
                .map(QueryTaskDTO::fromTask)
                .collect(Collectors.toList());
    }
}
