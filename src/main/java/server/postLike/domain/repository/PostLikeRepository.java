package server.postLike.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.tip.domain.Post;
import server.tip.domain.PostLike;
import server.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
    Long countByPost(Post post);
    List<PostLike> findAllByPost(Post post);
}