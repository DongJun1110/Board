package umc.velog.dto.member;

import lombok.*;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Member;
import umc.velog.domain.entity.Role;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String username;
    private String userId;
    private List<Board> boards; // Assuming only board IDs are needed in the DTO
    private String email;
    private Date createdDate;

    public static MemberDto toDto(Member memberEntity) {
        return MemberDto.builder()
                .id(memberEntity.getId())
                .username(memberEntity.getUsername())
                .userId(memberEntity.getUserId())
                .boards(memberEntity.getBoards())
                .email(memberEntity.getEmail())
                .createdDate(memberEntity.getCreatedDate())
                .build();
    }

}


