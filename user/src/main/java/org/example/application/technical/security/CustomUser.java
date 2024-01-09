package org.example.application.technical.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {

    private final long userID;

    public long getUserID() {
        return userID;
    }

    public CustomUser(long userID, String username, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userID = userID;
    }
}