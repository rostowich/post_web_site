package org.example.domain.user.model;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record User(
        Long id,
        String email,
        String firstname,
        String lastname,
        String password,
        String role,
        Timestamp creationDate
) {}
