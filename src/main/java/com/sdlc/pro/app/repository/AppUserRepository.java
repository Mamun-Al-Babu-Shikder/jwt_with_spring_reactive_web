package com.sdlc.pro.app.repository;

import com.sdlc.pro.app.model.AppUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AppUserRepository extends ReactiveCrudRepository<AppUser, Integer> {
    Mono<AppUser> findAppUserByUsername(String username);
}
