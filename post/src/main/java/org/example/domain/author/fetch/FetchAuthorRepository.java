package org.example.domain.author.fetch;

import java.util.Optional;

public interface FetchAuthorRepository {

    Optional<Author> fetchBy(String authorId);
}
