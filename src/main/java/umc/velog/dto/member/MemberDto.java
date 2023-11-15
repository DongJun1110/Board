package umc.velog.dto.member;

import lombok.*;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Comment;
import umc.velog.domain.entity.Member;
import umc.velog.domain.entity.Role;
import umc.velog.dto.comment.CommentDto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private Long id;
    private String username;
    private String userId;
    private List<Long> boardIds; // Assuming only board IDs are needed in the DTO
    private String password;
    private String email;
    private Date createdDate;
    private Role role;

    public static MemberDto toDto(Member memberEntity) {
        return MemberDto.builder()
                .id(memberEntity.getId())
                .username(memberEntity.getUsername())
                .userId(memberEntity.getUserId())
                .boardIds(memberEntity.getBoards().stream().map(Board::getId).collect(Collectors.toList()))
                .password(memberEntity.getPassword())
                .email(memberEntity.getEmail())
                .createdDate(memberEntity.getCreatedDate())
                .role(memberEntity.getRole())
                .build();
    }

}
