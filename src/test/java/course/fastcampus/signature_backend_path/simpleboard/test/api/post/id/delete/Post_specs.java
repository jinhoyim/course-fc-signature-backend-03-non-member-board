package course.fastcampus.signature_backend_path.simpleboard.test.api.post.id.delete;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import course.fastcampus.signature_backend_path.simpleboard.CreatePostCommandGenerator;
import course.fastcampus.signature_backend_path.simpleboard.board.model.BoardRequest;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostRepository;
import course.fastcampus.signature_backend_path.simpleboard.post.model.CreatePostCommand;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostAccessRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static course.fastcampus.signature_backend_path.simpleboard.TestSpecifiedLanguage.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class Post_specs {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private PostRepository postRepository;

    @ParameterizedTest
    @AutoSource
    @Customization(CreatePostCommandGenerator.class)
    void sut_correctly_delete_post(BoardRequest boardRequest, CreatePostCommand cmd) {
        Long boardId = createBoard(webClient, boardRequest).getId();
        CreatePostCommand command = new CreatePostCommand(
                boardId, cmd.username(), cmd.password(), cmd.email(), cmd.title(), cmd.content());
        Long postId = createPost(webClient, command).id();
        var deleteRequest = new PostAccessRequest(command.password());

        WebTestClient.ResponseSpec responseSpec = requestDeletePost(webClient, postId, deleteRequest);
        Optional<PostEntity> postEntity = postRepository.findById(postId);

        responseSpec.expectStatus().isOk();
        assertThat(postEntity.isPresent()).isTrue();
    }

    @Test
    void sut_return_Ok_if_not_exist() {
        var deleteRequest = new PostAccessRequest("pass");

        long notExistsId = -1L;
        requestDeletePost(webClient, notExistsId, deleteRequest)
                .expectStatus().isOk();
    }

    @ParameterizedTest
    @AutoSource
    @Customization(CreatePostCommandGenerator.class)
    void sut_return_BadRequest_not_equals_password(BoardRequest boardRequest, CreatePostCommand cmd) {
        Long boardId = createBoard(webClient, boardRequest).getId();
        CreatePostCommand command = new CreatePostCommand(
                boardId, cmd.username(), cmd.password(), cmd.email(), cmd.title(), cmd.content());
        Long postId = createPost(webClient, command).id();

        var deleteRequest = new PostAccessRequest("invalid password");
        requestDeletePost(webClient, postId, deleteRequest)
                .expectStatus().isBadRequest();
    }
}
