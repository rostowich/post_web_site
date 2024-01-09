package org.example.application.model;

import lombok.Builder;

@Builder
public record AuthorResponse(
        Long authorId,
        String email,
        String firstname,
        String lastname
) {
}
