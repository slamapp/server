package org.slams.server.common.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yunyun on 2022/05/24.
 */


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/health")
public class HeathCheckController {
    @GetMapping
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("ok");
    }
}
