package course.fastcampus.signature_backend_path.simpleboard.post.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findByIdAndStatus(Long id, PostStatus postStatus);

    List<PostEntity> findAllByStatus(PostStatus postStatus);
}
