package org.example.domain.comment.add;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.author.fetch.FetchAuthorRepository;
import org.example.domain.comment.model.FullComment;
import org.example.domain.comment.model.LightComment;
import org.example.domain.common.exceptions.NotFoundException;
import org.example.domain.post.fetch.FetchPostRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AddCommentService {
    private final AddCommentRepository addCommentRepository;
    private final FetchAuthorRepository fetchAuthorRepository;
    private final FetchPostRepository fetchPostRepository;

    public AddCommentService(AddCommentRepository addCommentRepository, FetchAuthorRepository fetchAuthorRepository, FetchPostRepository fetchPostRepository) {
        this.addCommentRepository = addCommentRepository;
        this.fetchAuthorRepository = fetchAuthorRepository;
        this.fetchPostRepository = fetchPostRepository;
    }

    public FullComment add (Long postId, LightComment lightComment){
        log.info("Saving comment for authorId :{} with those information :{}",
                postId, lightComment);
        var author = fetchAuthorRepository.fetchBy(lightComment.userId())
                .orElseThrow(() -> new NotFoundException("Author not found"));
        var postToAddComment = fetchPostRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        var commentSaved = addCommentRepository
                .add(postToAddComment.id(), lightComment);

        return FullComment.builder()
                .author(author)
                .id(commentSaved.id())
                .publishDate(commentSaved.publishDate())
                .description(commentSaved.description())
                .postId(commentSaved.postId())
                .build();
    }
}
