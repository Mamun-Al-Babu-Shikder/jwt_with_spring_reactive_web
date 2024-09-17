package com.sdlc.pro.app.filter;

import com.sdlc.pro.app.repository.AppUserRepository;
import com.sdlc.pro.app.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {
    private JwtService jwtService;
    private AppUserRepository userRepository;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var token = extractJwtFromHeader(exchange);
        if (token != null) {
            var username = jwtService.extractUsername(token);
            return userRepository.findAppUserByUsername(username)
                    .map(user -> new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            null,
                            Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
                    ))
                    .flatMap(auth -> chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
                    );
        }

        return chain.filter(exchange);
    }

    private String extractJwtFromHeader(ServerWebExchange exchange) {
        var authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
