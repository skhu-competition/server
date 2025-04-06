package server.postLike.application;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.postLike.api.response.PostLikeStatusDto;
import server.postLike.domain.repository.PostLikeRepository;
import server.tip.domain.Post;
import server.tip.domain.PostLike;
import server.tip.domain.repository.PostRepository;
import server.user.domain.User;
import server.user.domain.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    // 좋아요 누르기 / 취소
    @Transactional
    public PostLikeStatusDto toggleLike(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Optional<PostLike> existing = postLikeRepository.findByUserAndPost(user, post);

        boolean liked;
        if (existing.isPresent()) {
            postLikeRepository.delete(existing.get());
            liked = false;
        } else {
            postLikeRepository.save(PostLike.builder().user(user).post(post).build());
            liked = true;
        }

        long likeCount = postLikeRepository.countByPost(post);

        return PostLikeStatusDto.builder()
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }

    // 좋아요 상태 + 수 조회
    @Transactional(readOnly = true)
    public PostLikeStatusDto getStatus(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        boolean liked = postLikeRepository.findByUserAndPost(user, post).isPresent();
        long likeCount = postLikeRepository.countByPost(post);

        return PostLikeStatusDto.builder()
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }
}