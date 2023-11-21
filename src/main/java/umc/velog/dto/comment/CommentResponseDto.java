package umc.velog.dto.comment;

import lombok.*;

import java.util.Date;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private Date createdAt;
    private String userName;

}
