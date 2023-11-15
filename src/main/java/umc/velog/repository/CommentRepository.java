package umc.velog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.velog.domain.entity.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long> {

}