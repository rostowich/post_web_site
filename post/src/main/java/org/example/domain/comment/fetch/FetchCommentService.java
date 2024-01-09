package org.example.domain.comment.fetch;

import org.example.domain.author.fetch.FetchAuthorRepository;
import org.example.domain.comment.model.Comment;
import org.example.domain.comment.model.FullComment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FetchCommentService {

    private final FetchCommentRepository fetchCommentRepository;
    private final FetchAuthorRepository fetchAuthorRepository;

    public FetchCommentService(FetchCommentRepository fetchCommentRepository, FetchAuthorRepository fetchAuthorRepository) {
        this.fetchCommentRepository = fetchCommentRepository;
        this.fetchAuthorRepository = fetchAuthorRepository;
    }

    public List<FullComment> getPostComments(Long postId){
        return fetchCommentRepository.getBy(postId)
                .stream()
                .map(this::toFullComment)
                .toList();
    }

    private FullComment toFullComment(Comment comment){
        return FullComment.builder()
                .postId(comment.postId())
                .author(fetchAuthorRepository.fetchBy(comment.author())
                        .orElse(null))
                .description(comment.description())
                .id(comment.id())
                .publishDate(comment.publishDate())
                .build();
    }
}
