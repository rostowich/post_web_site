package org.example.application.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record AddUserRequest(
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Not a valid email") String email,
    @NotEmpty(message = "Password must not be empty")
    String password,
    @NotEmpty(message = "Firstname must not be empty")
    String firstname,
    @NotEmpty(message = "Lastname must not be empty")
    String lastname
) {
    @Override
    public String toString() {
        return "AddUserRequest{" +
                "email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
