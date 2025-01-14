package course.fastcampus.signature_backend_path.simpleboard.test.api.board;

import autoparams.AutoSource;
import course.fastcampus.signature_backend_path.simpleboard.TestSpecifiedLanguage;
import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardEntity;
import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardStatus;
import course.fastcampus.signature_backend_path.simpleboard.board.model.BoardRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "300")
class Post_specs {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebTestClient webClient;

    @ParameterizedTest
    @AutoSource
    void sut_create_board(BoardRequest boardRequest) {
        ResponseSpec spec = TestSpecifiedLanguage.requestCreateBoard(webClient, boardRequest);

        EntityExchangeResult<BoardEntity> response = spec.expectBody(BoardEntity.class).returnResult();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        BoardEntity body = response.getResponseBody();
        assertAll(
                () -> assertThat(body).isNotNull(),
                () -> assertThat(body.getId()).isPositive(),
                () -> assertThat(body.getBoardName()).isEqualTo(boardRequest.boardName()),
                () -> assertThat(body.getStatus()).isEqualTo(BoardStatus.REGISTERED)
        );
    }

    @ParameterizedTest
    @AutoSource
    void sut_create_board_json_path(BoardRequest boardRequest) {
        ResponseSpec spec = TestSpecifiedLanguage.requestCreateBoard(webClient, boardRequest);

        spec
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.board_name").isEqualTo(boardRequest.boardName())
                        .jsonPath("$.status").isEqualTo(BoardStatus.REGISTERED)
                        .jsonPath("$.id").isNotEmpty()
                        .jsonPath("$.id").isNumber();
    }

    @ParameterizedTest
    @AutoSource
    void sut_create_board_test_rest_template(BoardRequest boardRequest) {
        String url = "http://localhost:" + port + "/api/board";
        ResponseEntity<BoardEntity> response = restTemplate.postForEntity(
                url,
                boardRequest,
                BoardEntity.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        BoardEntity body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getBoardName()).isEqualTo(boardRequest.boardName());
        assertThat(body.getStatus()).isEqualTo(BoardStatus.REGISTERED);
    }
}
