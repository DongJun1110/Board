package umc.velog.dto.comment;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CommentRequestDto {
    private String content;
}
