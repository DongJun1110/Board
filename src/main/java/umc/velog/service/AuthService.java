package umc.velog.service;

import umc.velog.security.TokenInfo;
import umc.velog.dto.UserInfoDto;
import umc.velog.dto.UserJoinDto;
import umc.velog.dto.UserLoginDto;

public interface AuthService {
    TokenInfo login(UserLoginDto userLoginDto);

    void join(UserJoinDto userJoinDto);

    UserInfoDto info();
}