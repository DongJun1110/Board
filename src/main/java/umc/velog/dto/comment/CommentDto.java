package umc.velog.dto.comment;

import lombok.*;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Comment;
import umc.velog.domain.entity.Member;
import umc.velog.dto.board.BoardDto;

import java.util.Date;
import java.util.List;

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
    private Member writer;

    public static CommentDto toDto(Comment commentEntity) {
        return CommentDto.builder()
                .id(commentEntity.getId())
                .content(commentEntity.getContent())
                .createdAt(commentEntity.getCreatedAt())
                .board(commentEntity.getBoard())
                .writer(commentEntity.getWriter())
                .build();
    }
}
