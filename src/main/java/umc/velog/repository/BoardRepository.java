package umc.velog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Member;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByWriterId(Long writerId);

}