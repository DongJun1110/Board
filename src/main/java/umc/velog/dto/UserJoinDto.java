package umc.velog.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import umc.velog.domain.entity.Role;

import java.util.Date;

@Data
public class UserJoinDto {

    private String userId;
    private String userName;
    private String password;
    private String confirmPassword;
    private String email;
    private Role role;

}