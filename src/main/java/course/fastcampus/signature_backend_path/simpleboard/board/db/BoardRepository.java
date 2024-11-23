package course.fastcampus.signature_backend_path.simpleboard.board.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
