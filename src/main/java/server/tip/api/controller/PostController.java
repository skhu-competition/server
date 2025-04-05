package server.tip.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.tip.api.dto.request.PostRequest;
import server.tip.api.dto.response.PostResponse;
import server.tip.application.PostService;
import java.util.List;

@RestController
@RequestMapping("/tip")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //게시글 작성
    @PostMapping("/create")
    public PostResponse createPost(@RequestParam Long userId, @RequestBody PostRequest request) {
        return postService.create(userId, request);
    }

    // 게시글 전체 또는 카테고리별 조회
    @GetMapping("/read/{categoryId}")
    public List<PostResponse> getPosts(Long categoryId) {
        if (categoryId != null) {
            return postService.getByCategory(categoryId);
        }
        return postService.getAll();
    }

    // 게시글 상세 조회
    @GetMapping("/read/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        return postService.getById(postId);
    }

    // 게시글 수정
    @PatchMapping("/update/{postId}")
    public PostResponse updatePost(@PathVariable Long postId, @RequestBody PostRequest request) {
        return postService.update(postId, request);
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.delete(postId);
    }
}