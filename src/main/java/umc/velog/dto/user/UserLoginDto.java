package umc.velog.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserLoginDto {
    String userId;
    String password;
}
