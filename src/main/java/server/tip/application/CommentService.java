package server.tip.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.tip.api.dto.request.CommentRequest;
import server.tip.api.dto.response.CommentResponse;
import server.tip.domain.Comment;
import server.tip.domain.Post;
import server.tip.domain.repository.CommentRepository;
import server.tip.domain.repository.PostRepository;
import server.user.domain.User;
import server.user.domain.repository.UserRepository;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //댓글 생성
    @Transactional
    public CommentResponse create(Long userId, Long postId, CommentRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(request.content())
                .build();

        commentRepository.save(comment);
        return toResponse(comment);
    }

    //댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponse> getByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(this::toResponse)
                .collect(toList());
    }

    //댓글 수정
    @Transactional
    public CommentResponse update(Long commentId, CommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글 없음."));
        comment.update(request.content());
        return toResponse(comment);
    }

    //댓글 삭제
    @Transactional
    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }


    //toResponse로 다 처리 해줌.
    private CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .authorName(comment.getUser().getName())
                .createdAt(comment.getCreatedAt().toString())
                .build();
    }
}