package course.fastcampus.signature_backend_path.simpleboard.board.db;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "board")
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boardName;

    @Enumerated(EnumType.STRING)
    private BoardStatus status;

    protected BoardEntity() {
        this("");
    }

    public BoardEntity(Long id) {
        this.id = id;
    }

    private BoardEntity(String boardName) {
        this.boardName = boardName;
        this.status = BoardStatus.REGISTERED;
    }

    public static BoardEntity create(String boardName){
        return new BoardEntity(boardName);
    }
}
