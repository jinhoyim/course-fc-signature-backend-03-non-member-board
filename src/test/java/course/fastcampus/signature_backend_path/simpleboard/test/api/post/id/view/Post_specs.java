package course.fastcampus.signature_backend_path.simpleboard.test.api.post.id.view;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import course.fastcampus.signature_backend_path.simpleboard.CreatePostCommandGenerator;
import course.fastcampus.signature_backend_path.simpleboard.board.model.BoardRequest;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostStatus;
import course.fastcampus.signature_backend_path.simpleboard.post.model.CreatePostCommand;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostAccessRequest;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostResponse;
import course.fastcampus.signature_backend_path.simpleboard.reply.db.ReplyEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static course.fastcampus.signature_backend_path.simpleboard.TestSpecifiedLanguage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class Post_specs {

    @Autowired
    private WebTestClient webClient;

    @ParameterizedTest
    @AutoSource
    @Customization(CreatePostCommandGenerator.class)
    void sut_correct_return_post_response(BoardRequest boardRequest, CreatePostCommand cmd) {
        // Arrange
        Long boardId = createBoard(webClient, boardRequest).getId();
        CreatePostCommand command = new CreatePostCommand(
                boardId, cmd.username(), cmd.password(), cmd.email(), cmd.title(), cmd.content());
        LocalDateTime postAt = LocalDateTime.now();
        Long postId = createPost(webClient, command).id();
        PostAccessRequest viewRequest = new PostAccessRequest(command.password());

        // Act
        PostResponse actual = viewPost(webClient, postId, viewRequest);

        // Assertion
        String username = command.username();
        PostStatus registered = PostStatus.REGISTERED;
        LocalDateTime ignore1 = LocalDateTime.MIN;
        ArrayList<ReplyEntity> ignore2 = new ArrayList<>();

        var expected = new PostResponse(
                postId, username, command.email(), registered, command.title(), command.content(), ignore1, ignore2);

        assertAll(
                () -> assertThat(actual)
                        .usingRecursiveComparison()
                        .ignoringFields("postedAt", "replies")
                        .isEqualTo(expected),
                () -> assertThat(actual.postedAt())
                        .isCloseTo(postAt, Assertions.within(500, ChronoUnit.MILLIS)),
                () -> assertThat(actual.replies()).isEmpty()
        );
    }

    @Test
    void sut_return_NotFound_if_not_exist() {
        var viewRequest = new PostAccessRequest("pass");
        requestViewPost(webClient, -1L, viewRequest)
                .expectStatus().isNotFound();
    }

    @ParameterizedTest
    @AutoSource
    @Customization(CreatePostCommandGenerator.class)
    void sut_return_BadRequest_not_equals_password(BoardRequest boardRequest, CreatePostCommand cmd) {
        Long boardId = createBoard(webClient, boardRequest).getId();
        CreatePostCommand command = new CreatePostCommand(
                boardId, cmd.username(), cmd.password(), cmd.email(), cmd.title(), cmd.content());
        Long postId = createPost(webClient, command).id();

        var viewRequest = new PostAccessRequest("invalid password");
        requestViewPost(webClient, postId, viewRequest)
                .expectStatus().isBadRequest();
    }
}
