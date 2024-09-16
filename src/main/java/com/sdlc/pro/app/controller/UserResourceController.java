package com.sdlc.pro.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserResourceController {

    @GetMapping("/resource")
    public Mono<?> userResource() {
        var data = Map.of("data", "simple user data");
        return Mono.just(data);
    }
}
