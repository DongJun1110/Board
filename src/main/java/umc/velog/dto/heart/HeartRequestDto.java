package umc.velog.dto.heart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HeartRequestDto {

    private Long memberId;
    private Long boardId;

}
