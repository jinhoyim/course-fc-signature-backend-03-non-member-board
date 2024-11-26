package course.fastcampus.signature_backend_path.simpleboard.board.db;

import autoparams.AutoSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoardRepository_specs {

    @Autowired
    private BoardRepository sut;

    @ParameterizedTest
    @AutoSource
    void sut_correctly_adds_entity(String boardName) {
        BoardEntity boardEntity = BoardEntity.create(boardName);
        BoardEntity saved = sut.save(boardEntity);

        Optional<BoardEntity> actual = sut.findById(saved.getId());
        assertThat(actual).hasValueSatisfying(entity -> {
            assertThat(entity.getId()).isPositive();
            assertThat(entity.getBoardName()).isEqualTo(boardName);
        });
    }
}
