package org.example.application.model;

import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record UserResponse(
        Long id,
        String email,
        String firstname,
        String lastname,
        String role,
        Timestamp creationDate
) {
}
