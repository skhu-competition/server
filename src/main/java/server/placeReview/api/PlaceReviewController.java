package server.placeReview.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.placeReview.api.request.PlaceReviewReqDto;
import server.placeReview.api.request.PlaceReviewUpdateReqDto;
import server.placeReview.api.response.PlaceReviewListResDto;
import server.placeReview.api.response.PlaceReviewResDto;
import server.placeReview.application.PlaceReviewService;
import server.user.domain.User;
import server.user.domain.UserPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceReviewController {

    private final PlaceReviewService placeReviewService;

    // 후기 작성
    @PostMapping("/{placeId}/review")
    public PlaceReviewResDto reviewSave(
            @PathVariable Long placeId,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody PlaceReviewReqDto dto
    ) {
        return placeReviewService.reviewSave(userPrincipal.getUser().getUserId(), placeId, dto);
    }

    // 후기 조회 (장소 기준 전체 조회)
    @GetMapping("/{placeId}/review")
    public PlaceReviewListResDto reviewsFindByPlaceId(
            @PathVariable Long placeId
    ) {
        return placeReviewService.reviewsFindByPlaceId(placeId);
    }

    // 후기 수정
    @PatchMapping("/review/{reviewId}")
    public PlaceReviewResDto updateReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody PlaceReviewUpdateReqDto dto
    ) {
        return placeReviewService.updateReview(reviewId, userPrincipal.getUser().getUserId(), dto);
    }

    // 후기 삭제
    @DeleteMapping("/review/{reviewId}")
    public void deleteReview(
            @PathVariable Long reviewId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        placeReviewService.deleteReview(reviewId, userPrincipal.getUser().getUserId());
    }
}
