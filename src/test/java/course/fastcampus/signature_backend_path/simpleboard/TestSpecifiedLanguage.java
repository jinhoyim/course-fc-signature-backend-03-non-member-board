package course.fastcampus.signature_backend_path.simpleboard;

import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardEntity;
import course.fastcampus.signature_backend_path.simpleboard.board.model.BoardRequest;
import course.fastcampus.signature_backend_path.simpleboard.post.model.CreatePostCommand;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostAccessRequest;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostResponse;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.test.web.reactive.server.WebTestClient.*;

public class TestSpecifiedLanguage {

    public static BoardEntity createBoard(WebTestClient webClient, BoardRequest request) {
        return requestCreateBoard(webClient, request)
                .expectBody(BoardEntity.class)
                .returnResult()
                .getResponseBody();
    }

    public static ResponseSpec requestCreateBoard(WebTestClient webClient, BoardRequest request) {
        return webClient.post()
                .uri("/api/board")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange();
    }

    public static PostResponse createPost(WebTestClient webClient, CreatePostCommand request) {
        return requestCreatePost(webClient, request)
                .expectBody(PostResponse.class)
                .returnResult()
                .getResponseBody();
    }

    public static ResponseSpec requestCreatePost(WebTestClient webClient, CreatePostCommand postRequest) {
        return webClient.post()
                .uri("/api/post")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postRequest)
                .exchange();
    }

    public static PostResponse viewPost(WebTestClient webClient, Long postId, PostAccessRequest viewRequest) {
        return requestViewPost(webClient, postId, viewRequest)
                .expectBody(PostResponse.class)
                .returnResult()
                .getResponseBody();
    }

    public static ResponseSpec requestViewPost(WebTestClient webClient, Long postId, PostAccessRequest viewRequest) {
        return webClient.post()
                .uri("/api/post/" + postId + "/view")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(viewRequest)
                .exchange();
    }

    public static ResponseSpec requestDeletePost(WebTestClient webClient, Long postId, PostAccessRequest deleteRequest) {
        return webClient.post()
                .uri("/api/post/" + postId + "/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(deleteRequest)
                .exchange();
    }
}
