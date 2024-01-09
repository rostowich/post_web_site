package org.example.domain.post.fetch;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.author.fetch.FetchAuthorRepository;
import org.example.domain.post.model.FullPosts;
import org.example.domain.post.model.FullPosts.FullPost;
import org.example.domain.post.model.Post;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FetchPostService {

    private final FetchPostRepository fetchPostRepository;
    private final FetchAuthorRepository fetchAuthorRepository;

    public FetchPostService(FetchPostRepository fetchPostRepository, FetchAuthorRepository fetchAuthorRepository) {
        this.fetchPostRepository = fetchPostRepository;
        this.fetchAuthorRepository = fetchAuthorRepository;
    }

    public FullPosts getAll(){
        return new FullPosts(fetchPostRepository.fetchAll()
                .stream()
                .map(this::toFullPost)
                .toList());
    }

    private FullPost toFullPost(Post post) {
        return FullPost.builder()
                .author(fetchAuthorRepository.fetchBy(post.author()).orElse(null))
                .id(post.id())
                .publishDate(post.publishDate())
                .title(post.title())
                .description(post.description())
                .build();
    }
}
