package server.mypage.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.favoritePlace.api.response.FavoritePlaceResDto;
import server.mypage.api.dto.response.MypageResponse;
import server.mypage.application.MypageService;
import server.placeReview.api.response.PlaceReviewResDto;
import server.tip.api.dto.response.PostResponse;
import server.user.api.dto.request.UserInfo;
import server.user.domain.UserPrincipal;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    //유저 정보 조회 - List
    @GetMapping("/info")
    public MypageResponse getUserInfo(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return mypageService.getUserInfo(userPrincipal.getUser().getUserId());
    }

    /*//유저 정보 수정
    @PatchMapping("/{userId}")
    public UserInfo updateUserInfo(@RequestParam Long userId, @RequestBody UserInfo request) {
        return mypageService.update(userId, request);
    }*/

    //장소 즐겨찾기 조회
    @GetMapping("/place")
    public List<FavoritePlaceResDto> getFavorites(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return mypageService.getFavorites(userPrincipal.getUser().getUserId());
    }

    //사용자 작성 게시글 조회
    @GetMapping("/tip")
    public List<PostResponse> getMyPosts(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return mypageService.getMyPosts(userPrincipal.getUser().getUserId());
    }

    //사용자 후기 조회
    @GetMapping("/review")
    public List<PlaceReviewResDto> getMyReviews(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return mypageService.getMyReviews(userPrincipal.getUser().getUserId());
    }
}