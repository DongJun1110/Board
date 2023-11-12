package umc.velog.dto.board;

import jakarta.persistence.*;
import lombok.*;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Comment;
import umc.velog.domain.entity.Member;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String content;

    private Date createdAt;

    private Board board;

    private Member member;


    public static CommentDto toDto(Comment commentEntity) {
        return CommentDto.builder()
                .id(commentEntity.getId())
                .content(commentEntity.getContent())
                .createdAt(commentEntity.getCreatedAt())
                .board(commentEntity.getBoard())
                .member(commentEntity.getMember())
                .build();
    }
}
