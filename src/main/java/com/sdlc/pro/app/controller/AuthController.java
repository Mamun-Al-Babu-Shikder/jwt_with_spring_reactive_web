package com.sdlc.pro.app.controller;

import com.sdlc.pro.app.dto.AuthRequest;
import com.sdlc.pro.app.dto.JwtResponse;
import com.sdlc.pro.app.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private JwtService jwtService;
    private ReactiveAuthenticationManager reactiveAuthenticationManager;

    @PostMapping("/login")
    public Mono<JwtResponse> login(@RequestBody AuthRequest authRequest) {
        var auth = reactiveAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        return auth.map(authentication -> new JwtResponse(jwtService.generateToken(authentication.getName())));
    }
}
