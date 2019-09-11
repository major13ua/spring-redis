package com.example.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryTaskDTO {
    String id;
    @NotNull
    String param1;
    @NotNull
    String param2;

    public static QueryTaskDTO fromTask(QueryTask task) {
        return new QueryTaskDTO(task.getId(), task.getParam1(), task.getParam2());
    }

}
