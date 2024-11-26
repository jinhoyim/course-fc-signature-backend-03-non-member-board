package course.fastcampus.signature_backend_path.simpleboard.service;

import autoparams.AutoSource;
import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardEntity;
import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardRepository;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostRepository;
import course.fastcampus.signature_backend_path.simpleboard.post.model.CreatePostCommand;
import course.fastcampus.signature_backend_path.simpleboard.post.service.CreatePostCommandExecutor;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
public class CreatePostCommandExecutor_specs {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CreatePostCommandExecutor createPostCommandExecutor;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ParameterizedTest
    @AutoSource
    void sut_correctly_creates_post(
            String title,
            String content,
            String boardName) {
        // Arrange
        String username = "testuser";
        String password = "pass!";
        String email = "a@a.a";
        Long boardId = getBoardId(boardName);

        var command = new CreatePostCommand(boardId, username, password, email, title, content);

        // Act
        Long postId = createPostCommandExecutor.execute(command).id();

        // Assert
        PostEntity actual = postRepository.findById(postId).get();
        assertAll(
                () -> assertThat(actual.getUsername()).isEqualTo(username),
                () -> assertThat(actual.getEmail()).isEqualTo(email),
                () -> assertThat(actual.getTitle()).isEqualTo(title),
                () -> assertThat(actual.getContent()).isEqualTo(content)
        );
    }

    @ParameterizedTest
    @AutoSource
    void sut_correctly_sets_password_hash(
            String title,
            String content,
            String boardName) {
        // Arrange
        String username = "testuser";
        String password = "pass!";
        String email = "a@a.a";
        Long boardId = getBoardId(boardName);

        var command = new CreatePostCommand(boardId, username, password, email, title, content);

        // Act
        Long postId = createPostCommandExecutor.execute(command).id();

        // Assert
        PostEntity post = postRepository.findById(postId).get();
        boolean actual = passwordEncoder.matches(password, post.getPassword());
        assertThat(actual).isTrue();
    }

    private Long getBoardId(String boardName) {
        BoardEntity boardEntity = BoardEntity.create(boardName);
        Long boardId = boardRepository.save(boardEntity).getId();
        return boardId;
    }
}
