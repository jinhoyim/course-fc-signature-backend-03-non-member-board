package course.fastcampus.signature_backend_path.simpleboard.board.service;

import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardEntity;
import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardRepository;
import course.fastcampus.signature_backend_path.simpleboard.board.model.BoardRequest;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardEntity create(BoardRequest boardRequest) {
        BoardEntity board = BoardEntity.create(boardRequest.boardName());
        return boardRepository.save(board);
    }
}
