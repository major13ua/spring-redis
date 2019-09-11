package com.example.redis.repository;

import com.example.redis.model.QueryTask;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<QueryTask, String> {

}
