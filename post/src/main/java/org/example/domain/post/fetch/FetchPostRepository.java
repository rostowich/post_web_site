package org.example.domain.post.fetch;

import org.example.domain.post.model.Post;

import java.util.List;
import java.util.Optional;

public interface FetchPostRepository {
    List<Post> fetchAll();
    Optional<Post> findById(Long authorId);
}
