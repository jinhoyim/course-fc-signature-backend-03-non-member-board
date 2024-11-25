package course.fastcampus.signature_backend_path.simpleboard.post.service;

import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardEntity;
import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardRepository;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostRepository;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostStatus;
import course.fastcampus.signature_backend_path.simpleboard.post.exception.PostPasswordMismatchException;
import course.fastcampus.signature_backend_path.simpleboard.post.model.*;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyEntity;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyRepository;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final PasswordEncoder passwordEncoder;
    private final BoardRepository boardRepository;

    public PostService(
            PostRepository postRepository,
            ReplyRepository replyRepository,
            PasswordEncoder passwordEncoder, BoardRepository boardRepository) {
        this.postRepository = postRepository;
        this.replyRepository = replyRepository;
        this.passwordEncoder = passwordEncoder;
        this.boardRepository = boardRepository;
    }

    public PostResponse create(PostRequest postRequest) {
        // FK가 없으므로 BoardEntity를 조회하지 않으려면 존재하는지 먼저 확인이 필요하다.
        if (boardRepository.existsById(postRequest.boardId())) {
            PostEntity post = PostEntity.create(
                    // Id만 포함된 게시판 생성
                    new BoardEntity(postRequest.boardId()),
                    postRequest.username(),
                    passwordEncoder.encode(postRequest.password()),
                    postRequest.email(),
                    postRequest.title(),
                    postRequest.content(),
                    LocalDateTime.now()
            );

            PostEntity saved = postRepository.save(post);
            return PostConverter.fromEntity(saved);
        } else {
            throw new NoSuchElementException("Board does not exist");
        }
    }

    public PostResponse view(Long id, PostAccessRequest request) {
        return postRepository.findByIdAndStatus(id, PostStatus.REGISTERED).map(post -> {
            String requestPassword = request.password();
            String postPassword = post.getPassword();
            if (!passwordEncoder.matches(requestPassword, postPassword)){
                throw new PostPasswordMismatchException();
            }

            List<ReplyEntity> replies = replyRepository.findAllByPostIdAndStatusOrderByRepliedAtAsc(
                    post.getId(),
                    ReplyStatus.REGISTERED);

            return PostConverter.fromEntity(post, replies);
        }).orElseThrow();
    }

    public List<PostListItem> all() {
        return postRepository.findAllByStatusOrderByPostedAtDesc(PostStatus.REGISTERED).stream()
                .map(PostListItem::of)
                .toList();
    }

    public void delete(Long id, PostAccessRequest request) {
        postRepository.findById(id).ifPresent(post -> {
            String requestPassword = request.password();
            String postPassword = post.getPassword();
            if (!passwordEncoder.matches(requestPassword, postPassword)){
                throw new PostPasswordMismatchException();
            }

            post.remove();
            this.postRepository.save(post);
        });
    }
}
