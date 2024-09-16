package com.sdlc.pro.app.config;

import com.sdlc.pro.app.filter.JwtAuthenticationFilter;
import com.sdlc.pro.app.model.AppUser;
import com.sdlc.pro.app.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@AllArgsConstructor
public class AppSecurityConfig {
    private static final String[] PERMITTED_URLS = {"/api/login"};

    private AppUserService appUserService;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(PERMITTED_URLS).permitAll()
                        .pathMatchers("/api/user/**").hasAuthority("ROLE_USER")
                        .pathMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                        .anyExchange()
                        .permitAll()
                )
                .authenticationManager(reactiveAuthenticationManager())
                .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        var authManager =  new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService());
        authManager.setPasswordEncoder(passwordEncoder());
        return authManager;
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        var users = appUserService.getUsers()
                .stream()
                .map(this::toUserDetails)
                .toList();
        return new MapReactiveUserDetailsService(users);
    }

    private UserDetails toUserDetails(AppUser user) {
        return User.builder()
                .username(user.username())
                .password(passwordEncoder().encode(user.password()))
                .authorities(user.role())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
