package server.mypage.api.dto.response;

import lombok.Builder;
import server.favoritePlace.api.response.FavoritePlaceResDto;
import server.placeReview.api.response.PlaceReviewResDto;
import server.tip.api.dto.response.PostResponse;

import java.util.List;

@Builder
public record MypageResponse(
        String name,
        String profileImage,

        List<PostResponse> posts,
        List<PlaceReviewResDto> reviews,
        List<FavoritePlaceResDto> favorites
) {}