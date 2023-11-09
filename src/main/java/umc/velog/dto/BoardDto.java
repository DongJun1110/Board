package umc.velog.dto;

import jakarta.persistence.Entity;
import lombok.*;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Member;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private Member writer;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private int likeCount;
    private int viewCount;

    public static BoardDto toDto(Board boardEntity) {
        return BoardDto.builder()
                .id(boardEntity.getId())
                .writer(boardEntity.getWriter())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .createdDate(boardEntity.getCreatedDate())
                .modifiedDate(boardEntity.getModifiedDate())
                .likeCount(boardEntity.getLikeCount())
                .viewCount(boardEntity.getViewCount())
                .build();
    }

}
