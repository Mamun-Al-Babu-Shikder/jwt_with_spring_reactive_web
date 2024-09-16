package com.sdlc.pro.app.model;

import java.util.Objects;

public record AppUser(String username, String password, String role) {
    public AppUser {
        Objects.requireNonNull(username, "username can't be null value");
        Objects.requireNonNull(password, "password can't be null value");
        Objects.requireNonNull(role, "role can't be null value");
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof AppUser user) {
            return this.username.equals(user.username());
        }
        return false;
    }
}
