package server.postLike.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.postLike.api.response.PostLikeStatusDto;
import server.postLike.application.PostLikeService;

@RestController
@RequestMapping("/tip/like")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    // 좋아요 토글 (추가 또는 취소)
    @PostMapping("/{postId}")
    public ResponseEntity<PostLikeStatusDto> toggleLike(
            @RequestParam Long userId,
            @PathVariable Long postId) {
        return ResponseEntity.ok(postLikeService.toggleLike(userId, postId));
    }

    // 좋아요 상태 + 개수 조회
    @GetMapping("/{postId}/status")
    public ResponseEntity<PostLikeStatusDto> getLikeStatus(
            @RequestParam Long userId,
            @PathVariable Long postId) {
        return ResponseEntity.ok(postLikeService.getStatus(userId, postId));
    }
}

