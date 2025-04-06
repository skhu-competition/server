package server.tip.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.tip.api.dto.request.CommentRequest;
import server.tip.api.dto.response.CommentResponse;
import server.tip.application.CommentService;
import server.user.domain.UserPrincipal;

import java.util.List;

@RestController
@RequestMapping("/tip")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/{postId}/comment")
    public CommentResponse createComment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long postId,
            @RequestBody CommentRequest request) {
        return commentService.create(userPrincipal.getUser().getUserId(), postId, request);
    }

    //특정 게시글 댓글 전체 조회
    @GetMapping("/{postId}/comment")
    public List<CommentResponse> getComments(@PathVariable Long postId) {
        return commentService.getByPostId(postId);
    }

    //댓글 수정
    @PatchMapping("/{postId}/comment/{commentId}")
    public CommentResponse updateComment(@PathVariable Long commentId, @RequestBody CommentRequest request) {
        return commentService.update(commentId, request);
    }

    //댓글 삭제
    @DeleteMapping("/{postId}/comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }
}
