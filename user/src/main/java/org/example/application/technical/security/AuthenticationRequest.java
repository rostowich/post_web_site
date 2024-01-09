package org.example.application.technical.security;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record AuthenticationRequest(
        @NotEmpty(message = "Email must not be empty")
        @Email(message = "Not a valid email")
        String email,

        @NotEmpty(message = "Password must not be empty")
        String password
) {
}
