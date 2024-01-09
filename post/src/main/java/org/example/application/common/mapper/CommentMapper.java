package org.example.application.common.mapper;

import org.example.application.model.AddCommentRequest;
import org.example.application.model.CommentResponse;
import org.example.domain.comment.model.FullComment;
import org.example.domain.comment.model.LightComment;

import static org.example.application.common.mapper.PostMapper.mapToAuthor;

public final class CommentMapper {

    private CommentMapper() {
    }

    public static LightComment mapToComment(AddCommentRequest addCommentRequest, String userId){
        return LightComment.builder()
                .userId(userId)
                .description(addCommentRequest.description())
                .build();
    }

    public static CommentResponse mapToCommentResponse(FullComment fullComment){
        return CommentResponse.builder()
                .author(mapToAuthor(fullComment.author()))
                .postId(fullComment.postId())
                .description(fullComment.description())
                .publishDate(fullComment.publishDate())
                .id(fullComment.id())
                .build();
    }
}
