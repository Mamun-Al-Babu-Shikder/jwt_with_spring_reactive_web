package com.sdlc.pro.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminResourceController {

    @GetMapping("/resource")
    public Mono<?> adminResource() {
        var data = Map.of("data", "simple admin data");
        return Mono.just(data);
    }
}
