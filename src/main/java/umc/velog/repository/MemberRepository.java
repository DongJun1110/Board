package umc.velog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.velog.domain.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);

}
