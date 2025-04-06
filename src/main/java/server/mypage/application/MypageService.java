package server.mypage.application;

import server.placeReview.domain.repository.PlaceReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.favoritePlace.api.response.FavoritePlaceResDto;
import server.favoritePlace.domain.FavoritePlace;
import server.favoritePlace.domain.repository.FavoritePlaceRepository;
import server.mypage.api.dto.response.MypageResponse;
import server.place.domain.repository.PlaceRepository;
import server.placeReview.api.response.PlaceReviewResDto;
import server.tip.api.dto.response.PostResponse;
import server.tip.domain.repository.PostRepository;
import server.user.api.dto.request.UserInfo;
import server.user.domain.User;
import server.user.domain.repository.UserRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final FavoritePlaceRepository favoritePlaceRepository;

    //유저 정보 조회
    @Transactional(readOnly = true)
    public MypageResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 접근"));
        return MypageResponse.builder()
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .build();
    }

    //유저 정보 수정
    @Transactional
    public UserInfo update(Long userId, UserInfo request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저에 대한 정보를 찾을 수 없습니다."));
        user.update(request.email(), request.name(), request.profileImageUrl());
        return UserInfo.builder()
                .email(user.getEmail())
                .name(user.getName())
                .profileImageUrl(user.getProfileImage())
                .build();

    }

    //장소 즐겨찾기
    @Transactional(readOnly = true)
    public List<FavoritePlaceResDto> getFavorites(Long userId) {
        List<FavoritePlace> favorites = favoritePlaceRepository.findAllByUser_UserId(userId);
        return favorites.stream()
                .map(f -> FavoritePlaceResDto.from(f, "조회"))
                .toList();
    }

    // 사용자가 직접 작성한 게시글 조회
    @Transactional(readOnly = true)
    public List<PostResponse> getMyPosts(Long userId) {
        return postRepository.findById(userId).stream()
                .map(p -> PostResponse.builder()
                        .postId(p.getId())
                        .title(p.getTitle())
                        .content(p.getContent())
                        .category(p.getCategory().getName())
                        .image(p.getImage())
                        .favorite(p.isFavorite())
                        .createdAt(p.getCreatedAt().toString())
                        .updateAt(p.getUpdateAt().toString())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PlaceReviewResDto> getMyReviews(Long userId) {
        return placeReviewRepository.findAllByUser_UserId(userId).stream()
                .map(r -> PlaceReviewResDto.builder()
                        .reviewId(r.getId())
                        .userId(r.getUser().getUserId())
                        .userName(r.getUser().getName())
                        .content(r.getContent())
                        .rating(r.getRating())
                        .createdAt(r.getCreatedAt())
                        .modifiedAt(r.getModifiedAt())
                        .build())
                .toList();
    }
}