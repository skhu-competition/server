package server.tip.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.tip.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}