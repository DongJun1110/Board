package umc.velog.dto.heart;

import lombok.*;

@Getter
@Setter
@Builder
public class HeartDto {
    private Long memberId;
    private Long boardId;

    public HeartDto(Long memberId, Long boardId) {
        this.memberId = memberId;
        this.boardId = boardId;
    }
}