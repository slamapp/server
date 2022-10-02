package org.slams.server.common.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/health")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("ok");
    }
}
