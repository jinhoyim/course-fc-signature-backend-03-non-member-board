package course.fastcampus.signature_backend_path.simpleboard.test.api.post;

import autoparams.AutoSource;
import autoparams.ValueAutoSource;
import course.fastcampus.signature_backend_path.simpleboard.board.model.BoardRequest;
import course.fastcampus.signature_backend_path.simpleboard.post.model.CreatePostCommand;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static course.fastcampus.signature_backend_path.simpleboard.TestSpecifiedLanguage.createBoard;
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "500")
class Post_specs {

    @Autowired
    private WebTestClient webClient;

    @ParameterizedTest
    @AutoSource
    void sut_correctly_creates_post(BoardRequest boardRequest) {
        Long boardId = createBoard(webClient, boardRequest).getId();
        var postRequest = new CreatePostCommand(boardId, "tester1", "pass", "a@a.a", "title", "content");

        ResponseSpec spec = webClient.post()
                .uri("/api/post")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postRequest)
                .exchange();

        spec.expectStatus().isOk();
    }

    @ParameterizedTest
    @ValueAutoSource(strings = {"pas", "abc456789012345678901"})
    void sut_returns_BadRequest_if_invalid_password(
            String invalidPassword,
            String username,
            String title,
            String content,
            BoardRequest boardRequest
    ) {
        // Arrange
        String email = "a@a.a";
        Long boardId = createBoard(webClient, boardRequest).getId();
        var postRequest = new CreatePostCommand(boardId, username, invalidPassword, email, title, content);

        // Act
        ResponseSpec spec = webClient.post()
                .uri("/api/post")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postRequest)
                .exchange();

        // Act
        spec.expectStatus().isBadRequest();
    }

    @ParameterizedTest
    @ValueAutoSource(strings = {"aaaaa", "abc@aaa-"})
    void sut_returns_BadRequest_invalid_email(
            String invaliEmail,
            String username,
            String title,
            String content,
            BoardRequest boardRequest
    ) {
        // Arrange
        String password = "pass!";
        Long boardId = createBoard(webClient, boardRequest).getId();
        var postRequest = new CreatePostCommand(boardId, username, password, invaliEmail, title, content);

        // Act
        ResponseSpec spec = webClient.post()
                .uri("/api/post")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postRequest)
                .exchange();

        // Act
        spec.expectStatus().isBadRequest();
    }
}
