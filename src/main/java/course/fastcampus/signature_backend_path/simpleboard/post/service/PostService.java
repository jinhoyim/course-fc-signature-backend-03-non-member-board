package course.fastcampus.signature_backend_path.simpleboard.post.service;

import course.fastcampus.signature_backend_path.simpleboard.post.db.PostEntity;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostRepository;
import course.fastcampus.signature_backend_path.simpleboard.post.exception.PostPasswordMismatchException;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostRequest;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostResponse;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostViewRequest;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    public PostService(
            PostRepository postRepository,
            PasswordEncoder passwordEncoder) {
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public PostResponse create(PostRequest postRequest) {
        PostEntity post = PostEntity.create(
                postRequest.boardId(),
                postRequest.username(),
                passwordEncoder.encode(postRequest.password()),
                postRequest.email(),
                postRequest.title(),
                postRequest.content(),
                LocalDateTime.now()
        );

        PostEntity saved = postRepository.save(post);
        return PostResponse.of(saved);
    }

    public PostResponse view(Long id, @Valid PostViewRequest request) {
        return postRepository.findById(id).map(post -> {
            String requestPassword = request.password();
            String postPassword = post.getPassword();
            if (!passwordEncoder.matches(requestPassword, postPassword)){
                throw new PostPasswordMismatchException();
            }

            return PostResponse.of(post);
        }).orElseThrow();
    }
}
