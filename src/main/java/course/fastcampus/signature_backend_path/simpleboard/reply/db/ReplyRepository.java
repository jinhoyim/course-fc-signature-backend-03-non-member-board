package course.fastcampus.signature_backend_path.simpleboard.reply.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    List<ReplyEntity> findAllByPostIdAndStatusOrderByRepliedAtAsc(
            Long postId,
            ReplyStatus status);
}
