package org.example.domain.post.add;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.author.fetch.FetchAuthorRepository;
import org.example.domain.common.exceptions.NotFoundException;
import org.example.domain.post.model.FullPosts.FullPost;
import org.example.domain.post.model.LightPost;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AddPostService {
    private final AddPostRepository addPostRepository;
    private final FetchAuthorRepository fetchAuthorRepository;

    public AddPostService(AddPostRepository addPostRepository, FetchAuthorRepository fetchAuthorRepository) {
        this.addPostRepository = addPostRepository;
        this.fetchAuthorRepository = fetchAuthorRepository;
    }

    public FullPost add(LightPost lightPost){
        log.info("Saving post with those information :{}", lightPost);
        var author = fetchAuthorRepository.fetchBy(lightPost.userId())
                .orElseThrow(() -> new NotFoundException("Author not found"));
        var postSaved = addPostRepository.add(lightPost);

        return FullPost.builder()
        .author(author)
        .id(postSaved.id())
        .publishDate(postSaved.publishDate())
                .description(postSaved.description())
        .title(postSaved.title())
        .build();
    }
}
