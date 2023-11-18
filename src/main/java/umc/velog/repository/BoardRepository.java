package umc.velog.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.velog.domain.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = "writer")
    List<Board> findAll();
    List<Board> findByTitleContaining(String keyword);
}