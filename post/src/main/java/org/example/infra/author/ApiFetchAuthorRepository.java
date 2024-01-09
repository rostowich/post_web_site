package org.example.infra.author;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.author.fetch.Author;
import org.example.domain.author.fetch.FetchAuthorRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class ApiFetchAuthorRepository implements FetchAuthorRepository {

    private final FeignFetchAuthorClient feignFetchAuthorClient;

    public ApiFetchAuthorRepository(FeignFetchAuthorClient feignFetchAuthorClient) {
        this.feignFetchAuthorClient = feignFetchAuthorClient;
    }

    @Override
    public Optional<Author> fetchBy(String authorId) {
        return Optional.of(toAuthor(feignFetchAuthorClient.fetchById(authorId)));
    }

    private Author toAuthor(AuthorResponse authorResponse) {
        return Author.builder()
                .lastname(authorResponse.lastname())
                .firstname(authorResponse.firstname())
                .authorId(authorResponse.id())
                .email(authorResponse.email())
                .build();
    }
}
