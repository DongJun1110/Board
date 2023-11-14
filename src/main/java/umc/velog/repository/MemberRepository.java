package umc.velog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllById(Long id);
}
