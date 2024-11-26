package course.fastcampus.signature_backend_path.simpleboard.post.service;

import course.fastcampus.signature_backend_path.simpleboard.post.db.PostRepository;
import course.fastcampus.signature_backend_path.simpleboard.post.db.PostStatus;
import course.fastcampus.signature_backend_path.simpleboard.post.model.PostListItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(
            PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostListItem> all() {
        return postRepository.findAllByStatusOrderByPostedAtDesc(PostStatus.REGISTERED).stream()
                .map(PostListItem::of)
                .toList();
    }
}
