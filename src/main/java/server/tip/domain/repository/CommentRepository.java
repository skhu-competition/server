package server.tip.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.tip.domain.Comment;
import server.tip.domain.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    Void deleteByPost(Post post);
}