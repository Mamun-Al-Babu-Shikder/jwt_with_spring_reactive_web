package com.sdlc.pro.app.service;

import com.sdlc.pro.app.model.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AppUserService {
    private final Set<AppUser> users = Set.of(
            new AppUser("app_user", "user_pass", "ROLE_USER"),
            new AppUser("admin", "admin_pass", "ROLE_ADMIN")
    );

    public List<AppUser> getUsers() {
        return List.copyOf(users);
    }

    public AppUser getUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.username().equals(username))
                .findFirst().orElseThrow(() -> new RuntimeException("User not found"));
    }

}
