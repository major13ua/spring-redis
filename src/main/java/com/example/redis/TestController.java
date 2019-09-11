package com.example.redis;

import com.example.redis.model.QueryTaskDTO;
import com.example.redis.service.DataService;
import com.example.redis.service.DirectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
@AllArgsConstructor
public class TestController {

    private final DataService service;
    private final DirectService dDervice;

    private static AtomicInteger cntr = new AtomicInteger();
    private static AtomicInteger value = new AtomicInteger();

    @GetMapping("/test")
    public ResponseEntity<?> ping() {
        log.info("Calling ping");
        return ResponseEntity.ok().lastModified(System.currentTimeMillis()).build();
    }

    @GetMapping("/add")
    public ResponseEntity<QueryTaskDTO> addItem() {
        QueryTaskDTO dto = new QueryTaskDTO();
        dto.setParam1("P1" + System.currentTimeMillis());
        dto.setParam2("P2" + System.currentTimeMillis());
        return ResponseEntity.ok().lastModified(System.currentTimeMillis()).body(service.addTask(dto));
    }

    @GetMapping("/items")
    public ResponseEntity<List<QueryTaskDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/lpush")
    public ResponseEntity<?> push() {
        final int state = cntr.incrementAndGet();
        int id = value.get();
        if (state % 2 == 0) {
            id = value.incrementAndGet();
        }
        QueryTaskDTO queryDTO = new QueryTaskDTO();
        queryDTO.setParam1("P1-" + id);
        queryDTO.setParam2("P2-" + id);
        try {
            return ResponseEntity.ok(dDervice.addItem(queryDTO));
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        }

    }

    @GetMapping("/lsize")
    public ResponseEntity<Long> size() {
        return ResponseEntity.ok(dDervice.size());
    }

    @GetMapping("/rpop")
    public ResponseEntity<?> pop() {
        return
                dDervice.next()
                        .map(item -> ResponseEntity.ok().body(item))
                        .orElseGet(() -> ResponseEntity.noContent().build());
    }

}
