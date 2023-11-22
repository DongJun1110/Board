package umc.velog.dto.heart;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeartResponseDto {

    private Long boardId;
    private Boolean isPressed;
    private String userName;

}
