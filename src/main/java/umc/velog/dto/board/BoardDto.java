package umc.velog.dto.board;

import lombok.*;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Member;
import umc.velog.dto.comment.CommentDto;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private Member writer;
    private Date createdDate;
    private int likeCount;
    private List<CommentDto> comments;

    public static BoardDto toDto(Board boardEntity) {
        return BoardDto.builder()
                .id(boardEntity.getId())
                .writer(boardEntity.getWriter())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .createdDate(boardEntity.getCreatedDate())
                .likeCount(boardEntity.getLikeCount())
                .build();
    }



}
