package server.tip.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.tip.api.dto.request.FatchRequest;
import server.tip.api.dto.request.PostRequest;
import server.tip.api.dto.response.PostResponse;
import server.tip.application.PostService;
import server.user.domain.UserPrincipal;

import java.util.List;

@RestController
@RequestMapping("/tip")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //게시글 작성
    @PostMapping()
    public PostResponse createPost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody PostRequest request) {
        return postService.create(userPrincipal.getUser().getUserId(), request);
    }

    // 게시글 전체 또는 카테고리별 조회
    @GetMapping("/categories/{categoryId}")
    public List<PostResponse> getPosts(Long categoryId) {
        if (categoryId != null) {
            return postService.getByCategory(categoryId);
        }
        return postService.getAll();
    }

    // 게시글 상세 조회
    @GetMapping("/{postId}")
    public PostResponse getPost(
            @PathVariable Long postId) {
        return postService.getById(postId);
    }

    // 게시글 수정
    @PatchMapping("/{postId}")
    public PostResponse updatePost(
            @PathVariable Long postId,
            @RequestBody FatchRequest request) {
        return postService.update(postId, request);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.delete(postId);
    }
}