package umc.velog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.velog.domain.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}