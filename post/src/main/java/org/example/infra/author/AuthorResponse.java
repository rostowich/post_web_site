package org.example.infra.author;

public record AuthorResponse(
    Long id,
    String email,
    String firstname,
    String lastname,
    String role
) {
}
