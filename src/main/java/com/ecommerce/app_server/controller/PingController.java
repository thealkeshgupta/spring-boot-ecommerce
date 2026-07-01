package com.ecommerce.app_server.controller;

import com.ecommerce.app_server.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PingController {

    private static final Logger logger = LoggerFactory.getLogger(PingController.class);
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        logger.info("Ping received");
        return ResponseEntity.ok("pong");
    }
}
