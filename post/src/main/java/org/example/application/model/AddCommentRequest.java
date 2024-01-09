package org.example.application.model;

import jakarta.validation.constraints.NotEmpty;

public record AddCommentRequest(
        @NotEmpty(message = "Description must not be empty")
        String description
) {}
