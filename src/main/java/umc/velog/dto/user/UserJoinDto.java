package umc.velog.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserJoinDto {
    private String userId;
    private String password;
    private String role;
    private String userName;
}