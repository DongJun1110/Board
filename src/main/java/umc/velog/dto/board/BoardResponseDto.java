package umc.velog.dto.board;

import lombok.*;
import umc.velog.domain.entity.Comment;
import umc.velog.domain.entity.Member;
import umc.velog.dto.comment.CommentDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String userId;
    private String title;
    private String content;
    private String userName;
    private Date createdDate;
    private int likeCount;
    private List<Comment> comments;
    private String postImg;
    private String nextBoardTitle;
    private Long nextBoardId;
    private String previousBoardTitle;
    private Long previousBoardId;
    private Boolean currentUserLikes;

}
