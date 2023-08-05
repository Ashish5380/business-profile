package com.personal.businessprofile.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/_healthz")
public interface PingApi {

    @GetMapping("/ping")
    ResponseEntity<String> ping();
}
