package course.fastcampus.signature_backend_path.simpleboard;

import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardEntity;
import course.fastcampus.signature_backend_path.simpleboard.board.model.BoardRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.test.web.reactive.server.WebTestClient.*;

public class TestSpecifiedLanguage {

    public static BoardEntity createBoard(WebTestClient webClient, BoardRequest request) {
        ResponseSpec response = requestCreateBoard(webClient, request);
        return response.expectBody(BoardEntity.class)
                .returnResult().getResponseBody();
    }

    public static ResponseSpec requestCreateBoard(WebTestClient webClient, BoardRequest request) {
        return webClient.post()
                .uri("/api/board")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange();
    }
}
