package server.mypage.application;

package server.mypage.application;

import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.place.domain.repository.PlaceRepository;
import server.tip.domain.repository.PostRepository;
import server.user.domain.User;
import server.user.domain.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PlaceRepository placeRepository;

    //유저 정보 조회
    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return UserInfoResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .build();
    }

    //유저 정보 수정
    @Transactional
    public void updateUserInfo(Long userId, UserInfoUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        user.updateInfo(request.name(), request.profileImage());
    }

    //장소 즐겨찾기 조회
    @Transactional(readOnly = true)
    public List<FavoriteSummary> getFavorites(Long userId) {
        return favoriteRepository.findByUserId(userId).stream()
                .map(f -> FavoriteSummary.builder()
                        .placeId(f.getPlace().getId())
                        .placeName(f.getPlace().getName())
                        .address(f.getPlace().getAddress())
                        .build())
                .toList();
    }

    // 사용자가 직접 작성한 게시글 조회
    @Transactional(readOnly = true)
    public List<PostSummary> getMyPosts(Long userId) {
        return postRepository.findByUserId(userId).stream()
                .map(p -> PostSummary.builder()
                        .postId(p.getId())
                        .title(p.getTitle())
                        .category(p.getCategory().getName())
                        .createdAt(p.getCreatedAt().toString())
                        .build())
                .toList();
    }

    // 사용자가 직접 작성한 후기 조회
    @Transactional(readOnly = true)
    public List<ReviewSummary> getMyReviews(Long userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(r -> ReviewSummary.builder()
                        .reviewId(r.getId())
                        .placeName(r.getPlace().getName())
                        .content(r.getContent())
                        .createdAt(r.getCreatedAt().toString())
                        .build())
                .toList();
    }
}