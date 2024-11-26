package course.fastcampus.signature_backend_path.simpleboard.post.controller;

import course.fastcampus.signature_backend_path.simpleboard.post.exception.PostPasswordMismatchException;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostListItem;
import course.fastcampus.signature_backend_path.simpleboard.post.model.CreatePostCommand;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostResponse;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostAccessRequest;
import course.fastcampus.signature_backend_path.simpleboard.post.service.CreatePostCommandExecutor;
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
    private final CreatePostCommandExecutor createPostCommandExecutor;

    public PostApiController(
            PostService postService,
            CreatePostCommandExecutor createPostCommandExecutor) {
        this.postService = postService;
        this.createPostCommandExecutor = createPostCommandExecutor;
    }

    @PostMapping
    public PostResponse create(@Valid @RequestBody CreatePostCommand postRequest) {
        return createPostCommandExecutor.execute(postRequest);
    }

    @PostMapping("{id}/view")
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

    @PostMapping("{postId}/delete")
    public ResponseEntity<Void> delete(
            @PathVariable Long postId,
            @Valid @RequestBody PostAccessRequest request) {
        try {
            postService.delete(postId, request);
            return ResponseEntity.ok().build();
        }
        catch (PostPasswordMismatchException pe) {
            return ResponseEntity.badRequest().build();
        }
    }
}
