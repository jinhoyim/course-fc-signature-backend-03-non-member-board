package course.fastcampus.signature_backend_path.simpleboard.board.controller;

import course.fastcampus.signature_backend_path.simpleboard.board.db.BoardEntity;
import course.fastcampus.signature_backend_path.simpleboard.board.model.BoardRequest;
import course.fastcampus.signature_backend_path.simpleboard.board.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/board")
public class BoardApiController {

    private final BoardService boardService;

    public BoardApiController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public BoardEntity create(@Valid @RequestBody BoardRequest boardRequest) {
        return boardService.create(boardRequest);
    }
}
