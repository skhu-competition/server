package server.favoritePost.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.favoritePost.domain.FavoritePost;
import server.tip.domain.Post;
import server.user.domain.User;
import java.util.List;
import java.util.Optional;

public interface FavoritePostRepository extends JpaRepository<FavoritePost, Long> {
    Optional<FavoritePost> findByUserAndPost(User user, Post post);
    List<FavoritePost> findAllByUser(User user);
}
