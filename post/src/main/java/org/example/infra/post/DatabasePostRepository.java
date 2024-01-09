package org.example.infra.post;

import org.example.domain.post.add.AddPostRepository;
import org.example.domain.post.fetch.FetchPostRepository;
import org.example.domain.post.model.LightPost;
import org.example.domain.post.model.Post;
import org.example.infra.common.DateTimeProvider;
import org.example.infra.entities.PostEntity;
import org.example.infra.repository.PostEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DatabasePostRepository implements AddPostRepository, FetchPostRepository {
    private final PostEntityRepository postEntityRepository;
    private final DateTimeProvider dateTimeProvider;

    public DatabasePostRepository(PostEntityRepository postEntityRepository, DateTimeProvider dateTimeProvider) {
        this.postEntityRepository = postEntityRepository;
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Post add(LightPost lightPost) {
        var postToSave = new PostEntity();
        postToSave.setTitle(lightPost.title());
        postToSave.setPublishDate(dateTimeProvider.now());
        postToSave.setUserId(Long.parseLong(lightPost.userId()));
        postToSave.setDescription(lightPost.description());
        return toPost(postEntityRepository.save(postToSave));
    }

    @Override
    public List<Post> fetchAll() {
        return postEntityRepository.findAll()
                .stream()
                .map(this::toPost)
                .toList();
    }

    @Override
    public Optional<Post> findById(Long authorId) {
        return postEntityRepository.findById(authorId)
                .map(this::toPost);
    }

    private Post toPost(PostEntity postEntity) {
        return Post.builder()
                .title(postEntity.getTitle())
                .description(postEntity.getDescription())
                .id(postEntity.getId())
                .author(String.valueOf(postEntity.getUserId()))
                .publishDate(postEntity.getPublishDate())
                .build();
    }
}
