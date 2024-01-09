package org.example.application.model;

import jakarta.validation.constraints.NotEmpty;

public record AddPostRequest(
        @NotEmpty(message = "Title must not be empty")
        String title,
        @NotEmpty(message = "Description must not be empty")
        String description
) {}
