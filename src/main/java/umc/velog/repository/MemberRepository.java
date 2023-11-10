package umc.velog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.velog.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
