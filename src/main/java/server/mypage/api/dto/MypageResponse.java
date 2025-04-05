package server.mypage.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record MypageResponse(
        String name,
        String profileImage,

        List<PostSummary> posts,
        List<ReviewSummary> reviews,
        List<FavoriteSummary> favorites
) {}