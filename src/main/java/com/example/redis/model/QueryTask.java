package com.example.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("queryTask")
public class QueryTask {

    @Id
    private String id;

    private String param1;

    private String param2;

    public static QueryTask fromDTO(QueryTaskDTO dto) {
        return new QueryTask("T-" + Objects.hash(dto.getParam1(), dto.getParam2()),
                dto.getParam1(),
                dto.getParam2());
    }

}
