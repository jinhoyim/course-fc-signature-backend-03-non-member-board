package course.fastcampus.signature_backend_path.simpleboard.post.controller;

import course.fastcampus.signature_backend_path.simpleboard.post.exception.PostPasswordMismatchException;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostRequest;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostResponse;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostViewRequest;
import course.fastcampus.signature_backend_path.simpleboard.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostApiController {

    private final PostService postService;

    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public PostResponse create(@Valid @RequestBody PostRequest postRequest) {
        return postService.create(postRequest);
    }

    @PostMapping("{id}")
    public ResponseEntity<PostResponse> view(
            @PathVariable Long id,
            @Valid @RequestBody PostViewRequest request) {
        try {
            return ResponseEntity.ok(postService.view(id, request));
        }
        catch (PostPasswordMismatchException pe) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("all")
    public List<PostResponse> list() {
        return postService.all();
    }
}
