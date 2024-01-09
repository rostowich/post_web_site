package org.example.application.common.mapper;

import org.example.application.model.AddPostRequest;
import org.example.application.model.AuthorResponse;
import org.example.application.model.FullPostResponse;
import org.example.domain.author.fetch.Author;
import org.example.domain.post.model.FullPosts.FullPost;
import org.example.domain.post.model.LightPost;

public final class PostMapper {

    private PostMapper() {
    }

    public static LightPost mapToPost(AddPostRequest addPostRequest, String userId){
        return LightPost.builder()
                .userId(userId)
                .title(addPostRequest.title())
                .description(addPostRequest.description())
                .build();
    }

    public static FullPostResponse mapToFullPostResponse(FullPost fullPost){
        return FullPostResponse.builder()
                .author(mapToAuthor(fullPost.author()))
                .title(fullPost.title())
                .description(fullPost.description())
                .publishDate(fullPost.publishDate())
                .id(fullPost.id())
                .build();
    }

    public static AuthorResponse mapToAuthor(Author author){
        return author != null
        ? AuthorResponse.builder()
                .authorId(author.authorId())
                .email(author.email())
                .firstname(author.firstname())
                .lastname(author.lastname())
                .build()
        : null;
    }
}
