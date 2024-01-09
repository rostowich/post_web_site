package org.example.domain.post.add;

import org.example.domain.post.model.LightPost;
import org.example.domain.post.model.Post;

public interface AddPostRepository {
    Post add(LightPost lightPost);
}
