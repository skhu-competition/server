package server.favoritePost.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.favoritePost.api.dto.FavoritePostResDto;
import server.favoritePost.application.FavoritePostService;

@RestController
@RequestMapping("/tip/favorite")
@RequiredArgsConstructor
public class FavoritePostController {

    private final FavoritePostService favoritePostService;

    // 즐겨찾기 추가
    @PostMapping("/{postId}")
    public ResponseEntity<FavoritePostResDto> addFavorite(
            @RequestParam Long userId,
            @PathVariable Long postId) {
        return ResponseEntity.ok(favoritePostService.addFavorite(userId, postId));
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeFavorite(
            @RequestParam Long userId,
            @PathVariable Long postId) {
        favoritePostService.removeFavorite(userId, postId);
        return ResponseEntity.noContent().build();
    }
}
