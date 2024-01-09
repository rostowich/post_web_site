package org.example.domain.author.fetch;

import lombok.Builder;

@Builder
public record Author(
        Long authorId,
        String email,
        String firstname,
        String lastname
) {
}
