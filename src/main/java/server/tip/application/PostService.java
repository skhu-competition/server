package server.tip.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.tip.api.dto.request.FatchRequest;
import server.tip.api.dto.request.PostRequest;
import server.tip.api.dto.response.PostResponse;
import server.tip.domain.Category;
import server.tip.domain.Post;
import server.tip.domain.repository.CategoryRepository;
import server.tip.domain.repository.PostRepository;
import server.user.domain.User;
import server.user.domain.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    //게시글 작성
    @Transactional
    public PostResponse create(Long userId, PostRequest request) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 사용자입니다."));
        Category category = categoryRepository
                .findById(request.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 카테고리입니다."));
        Post post = Post.builder()
                .user(user)
                .category(category)
                .title(request.title())
                .content(request.content())
                .image(request.image())
                .build();

        postRepository.save(post);
        return toResponse(post);
    }

    //게시글 전체 조회
    @Transactional(readOnly = true)
    public List<PostResponse> getAll() {
        return postRepository.findAll().stream()
                .map(this::toResponse)
                .collect(toList());
    }

    // 카테고리별 게시글 조회
    @Transactional(readOnly = true)
    public List<PostResponse> getByCategory(Long categoryId) {
        return postRepository.findByCategoryId(categoryId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    //게시글 조회
    @Transactional(readOnly = true)
    public PostResponse getById(Long postId) {
        return postRepository.findById(postId)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
    }

    //게시글 수정
    @Transactional
    public PostResponse update(Long postId, FatchRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        post.update(request.title(), request.content(), request.image());
        return toResponse(post);

    }

    //게시글 삭제
    @Transactional
    public void delete(Long postId) {
        postRepository.deleteById(postId);
    }

    private PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory().getName())
                .image(post.getImage())
                .createdAt(post.getCreatedAt().toString())
                .updateAt(post.getUpdateAt().toString())
                .userName(post.getUser().getName())
                .build();
    }
}
