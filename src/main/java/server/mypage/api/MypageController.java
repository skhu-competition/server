package server.mypage.api;

import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.mypage.api.dto.MypageResponse;
import server.mypage.application.MypageService;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    //유저 정보 조회
    @GetMapping("/info")
    public UserInfoResponse getUserInfo(@RequestParam Long userId) {
        return mypageService.getUserInfo(userId);
    }

    //유저 정보 수정
    @PatchMapping("/info/patch")
    public void updateUserInfo(@RequestParam Long userId, @RequestBody UserInfoUpdateRequest request) {
        mypageService.updateUserInfo(userId, request);
    }

    //목록
    @GetMapping("/favorites")
    public List<FavoriteSummary> getFavorites(@RequestParam Long userId) {
        return mypageService.getFavorites(userId);
    }

    //게시글
    @GetMapping("/posts")
    public List<PostSummary> getMyPosts(@RequestParam Long userId) {
        return mypageService.getMyPosts(userId);
    }

    //후기
    @GetMapping("/reviews")
    public List<ReviewSummary> getMyReviews(@RequestParam Long userId) {
        return mypageService.getMyReviews(userId);
    }
}