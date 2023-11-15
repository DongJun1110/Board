package umc.velog.service;

import umc.velog.security.TokenInfo;
import umc.velog.dto.user.UserInfoDto;
import umc.velog.dto.user.UserJoinDto;
import umc.velog.dto.user.UserLoginDto;

public interface AuthService {
    TokenInfo login(UserLoginDto userLoginDto);

    void join(UserJoinDto userJoinDto);

    UserInfoDto info();
}