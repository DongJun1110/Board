package umc.velog.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import umc.velog.domain.entity.Role;

@Data
@Getter
@Setter
public class UserJoinDto {

    private String userId;
    private String userName;
    private String password;
    private String confirmPassword;
    private String email;
    private Role role;

}