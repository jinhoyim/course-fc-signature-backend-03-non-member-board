package course.fastcampus.signature_backend_path.simpleboard.post.service;

import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardEntity;
import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardRepository;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostRepository;
import course.fastcampus.signature_backend_path.simpleboard.post.model.CreatePostCommand;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostConverter;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class CreatePostCommandExecutor {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;

    public CreatePostCommandExecutor(
            PostRepository postRepository,
            BoardRepository boardRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PostResponse execute(CreatePostCommand command) {
        // FK가 없으므로 BoardEntity를 조회하지 않으려면 존재하는지 먼저 확인이 필요하다.
        if (boardRepository.existsById(command.boardId())) {
            PostEntity post = PostEntity.create(
                    // Id만 포함된 게시판 생성
                    new BoardEntity(command.boardId()),
                    command.username(),
                    passwordEncoder.encode(command.password()),
                    command.email(),
                    command.title(),
                    command.content(),
                    LocalDateTime.now()
            );

            PostEntity saved = postRepository.save(post);
            return PostConverter.fromEntity(saved);
        } else {
            throw new NoSuchElementException("Board does not exist");
        }
    }
}
