package server.favoritePost.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.favoritePost.api.dto.FavoritePostResDto;
import server.favoritePost.domain.FavoritePost;
import server.favoritePost.domain.repository.FavoritePostRepository;
import server.tip.domain.Post;
import server.tip.domain.repository.PostRepository;
import server.user.domain.User;
import server.user.domain.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoritePostService {

    private final FavoritePostRepository favoritePostRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 즐겨찾기 추가
    @Transactional
    public FavoritePostResDto addFavorite(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        Optional<FavoritePost> existing = favoritePostRepository.findByUserAndPost(user, post);
        if (existing.isPresent()) {
            throw new IllegalStateException("이미 즐겨찾기된 게시글입니다.");
        }

        FavoritePost favorite = FavoritePost.builder()
                .user(user)
                .post(post)
                .build();

        favoritePostRepository.save(favorite);
        return FavoritePostResDto.from(favorite, "추가됨");
    }

    // 즐겨찾기 삭제
    @Transactional
    public void removeFavorite(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        FavoritePost favorite = favoritePostRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new IllegalStateException("즐겨찾기된 게시글이 아닙니다."));

        favoritePostRepository.delete(favorite);
    }

    // 즐겨찾기 목록
    @Transactional(readOnly = true)
    public List<FavoritePostResDto> getMyFavorites(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        return favoritePostRepository.findAllByUser(user).stream()
                .map(f -> FavoritePostResDto.from(f, "조회"))
                .toList();
    }

    // 즐겨찾기 햇는지 여부 조회
    @Transactional(readOnly = true)
    public boolean isFavorite(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        if (favoritePostRepository.findByUserAndPost(user, post).isPresent()) {
            throw new IllegalArgumentException("이미 즐겨찾기한 게시글입니다.");
        }

        return favoritePostRepository.findByUserAndPost(user, post).isPresent();
    }
}