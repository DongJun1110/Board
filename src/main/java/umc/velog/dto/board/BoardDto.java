package umc.velog.dto.board;

import lombok.*;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Member;

import java.util.Date;

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
    private int viewCount;

    public static BoardDto toDto(Board boardEntity) {
        return BoardDto.builder()
                .id(boardEntity.getId())
                .writer(boardEntity.getWriter())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .createdDate(boardEntity.getCreatedDate())
                .likeCount(boardEntity.getLikeCount())
                .viewCount(boardEntity.getViewCount())
                .build();
    }

}
