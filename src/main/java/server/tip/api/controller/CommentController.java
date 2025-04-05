package server.tip.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.tip.api.dto.request.CommentRequest;
import server.tip.api.dto.response.CommentResponse;
import server.tip.application.CommentService;

import java.util.List;

@RestController
@RequestMapping("/tip")
@RequiredArgsConstructor
public class CommentController {
    private CommentService commentService;

    //댓글 작성
    @PostMapping("/{postId}/comment")
    public CommentResponse createComment(@RequestParam Long userId, @PathVariable Long postId, @RequestBody CommentRequest request) {
        return commentService.create(userId, postId, request);
    }

    //댓글 전체 조회
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
