package org.example.application;

import lombok.extern.slf4j.Slf4j;
import org.example.application.common.mapper.CommentMapper;
import org.example.application.common.mapper.PostMapper;
import org.example.application.model.AddCommentRequest;
import org.example.application.model.AddPostRequest;
import org.example.application.model.CommentResponse;
import org.example.application.model.FullPostResponse;
import org.example.domain.comment.add.AddCommentService;
import org.example.domain.comment.fetch.FetchCommentService;
import org.example.domain.post.add.AddPostService;
import org.example.domain.post.fetch.FetchPostService;
import org.example.infra.security.AuthorizationHeaderFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {

    private final AddPostService addPostService;
    private final FetchPostService fetchPostService;
    private final AddCommentService addCommentService;
    private final FetchCommentService fetchCommentService;

    public PostController(AddPostService addPostService, FetchPostService fetchPostService, AddCommentService addCommentService, FetchCommentService fetchCommentService) {
        this.addPostService = addPostService;
        this.fetchPostService = fetchPostService;
        this.addCommentService = addCommentService;
        this.fetchCommentService = fetchCommentService;
    }

    @PostMapping()
    public ResponseEntity<FullPostResponse> addPost(@RequestBody AddPostRequest addPostRequest) {
        log.info("Adding a new post with those information : {}", addPostRequest);

        var postToCreate = PostMapper.mapToPost(addPostRequest, getAuthorId());
        var postCreated = addPostService.add(postToCreate);
        return ResponseEntity.ok(PostMapper.mapToFullPostResponse(postCreated));
    }

    @GetMapping()
    public ResponseEntity<List<FullPostResponse>> getAllPosts() {
        var allPosts = fetchPostService.getAll();
        return ResponseEntity.ok(allPosts.posts()
                .stream()
                .map(PostMapper::mapToFullPostResponse)
                .toList());
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable long postId,
            @RequestBody AddCommentRequest addCommentRequest) {
        log.info("Adding a new comment for the post: {} with the information : {}", postId, addCommentRequest);

        var commentToAdd = CommentMapper.mapToComment(addCommentRequest, getAuthorId());
        var commentCreated = addCommentService.add(postId, commentToAdd);
        return ResponseEntity.ok(CommentMapper.mapToCommentResponse(commentCreated));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getPostComments(
            @PathVariable long postId
    ) {
        var allPosts = fetchCommentService.getPostComments(postId);
        return ResponseEntity.ok(allPosts
                .stream()
                .map(CommentMapper::mapToCommentResponse)
                .toList());
    }

    private String getAuthorId() {
        return AuthorizationHeaderFilter.getIdFromJwtPrincipal();
    }
}
