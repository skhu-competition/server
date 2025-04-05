package server.favoritePlace.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.favoritePlace.api.response.FavoritePlaceListResDto;
import server.favoritePlace.api.response.FavoritePlaceResDto;
import server.favoritePlace.application.FavoritePlaceService;
import server.user.domain.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place/favorite")
public class FavoritePlaceController {

    private final FavoritePlaceService favoritePlaceService;

    // 즐겨찾기 추가 또는 삭제 (Toggle)
    @PostMapping("/{placeId}")
    public ResponseEntity<FavoritePlaceResDto> favoriteCreateAndDelete(
            @PathVariable Long placeId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        FavoritePlaceResDto favoritePlaceResDto = favoritePlaceService.favoriteCreateAndDelete(userPrincipal.getUser().getUserId(), placeId);

        return ResponseEntity.ok(favoritePlaceResDto);
    }


    // 즐겨찾기 목록 조회
    @GetMapping
    public ResponseEntity<FavoritePlaceListResDto> findByUserId(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        FavoritePlaceListResDto favoritePlaceListResDto = favoritePlaceService.findByUserId(userPrincipal.getUser().getUserId());
        return ResponseEntity.ok(favoritePlaceListResDto);
    }
}
