package server.mypage.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.favoritePlace.api.response.FavoritePlaceResDto;
import server.mypage.api.dto.response.MypageResponse;
import server.mypage.application.MypageService;
import server.placeReview.api.response.PlaceReviewResDto;
import server.tip.api.dto.response.PostResponse;
import server.user.api.dto.request.UserInfo;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    //유저 정보 조회 - List
    @GetMapping("/info")
    public MypageResponse getUserInfo(@RequestParam Long userId) {
        return mypageService.getUserInfo(userId);
    }

    //유저 정보 수정
    @PatchMapping("/{userId}")
    public UserInfo updateUserInfo(@RequestParam Long userId, @RequestBody UserInfo request) {
        return mypageService.update(userId, request);
    }

    //장소 즐겨찾기 조회
    @GetMapping("/place/{favoriteId}")
    public List<FavoritePlaceResDto> getFavorites(@RequestParam Long userId) {
        return mypageService.getFavorites(userId);
    }

    //사용자 작성 게시글 조회
    @GetMapping("/tip/{userId}")
    public List<PostResponse> getMyPosts(@RequestParam Long userId) {
        return mypageService.getMyPosts(userId);
    }

    //사용자 후기 조회
    @GetMapping("/place/{reviewId}")
    public List<PlaceReviewResDto> getMyReviews(@RequestParam Long userId) {
        return mypageService.getMyReviews(userId);
    }
}