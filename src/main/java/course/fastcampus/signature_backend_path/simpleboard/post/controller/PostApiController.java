package course.fastcampus.signature_backend_path.simpleboard.post.controller;

import course.fastcampus.signature_backend_path.simpleboard.post.exception.PostPasswordMismatchException;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostListItem;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostRequest;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostResponse;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostAccessRequest;
import course.fastcampus.signature_backend_path.simpleboard.post.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
            @Valid @RequestBody PostAccessRequest request) {
        try {
            return ResponseEntity.ok(postService.view(id, request));
        }
        catch (PostPasswordMismatchException pe) {
            return ResponseEntity.badRequest().build();
        }
        catch (NoSuchElementException nse) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("all")
    public List<PostListItem> list() {
        return postService.all();
    }

    @PostMapping("{id}/delete")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @Valid @RequestBody PostAccessRequest request) {
        try {
            postService.delete(id, request);
            return ResponseEntity.ok().build();
        }
        catch (PostPasswordMismatchException pe) {
            return ResponseEntity.badRequest().build();
        }
    }
}
