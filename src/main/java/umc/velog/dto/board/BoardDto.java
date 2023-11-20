package umc.velog.dto.board;

import lombok.*;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Member;
import umc.velog.dto.comment.CommentDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String writerId;
    private Date createdDate;
    private int likeCount;
    private List<CommentDto> comments;
    private String postImg;

    public static BoardDto toDto(Board boardEntity) {
        List<CommentDto> commentDtoList = boardEntity.getComments()
                .stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .writerId(boardEntity.getWriter().getUserId())
                .createdDate(boardEntity.getCreatedDate())
                .likeCount(boardEntity.getLikeCount())
                .comments(commentDtoList)
                .postImg(boardEntity.getPostImg())
                .build();
    }


}
