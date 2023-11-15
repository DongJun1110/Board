package umc.velog.service;

import umc.velog.domain.entity.Member;
import umc.velog.dto.user.UserJoinDto;
import umc.velog.security.TokenInfo;
import umc.velog.dto.user.UserInfoDto;
import umc.velog.dto.user.UserLoginDto;

public interface AuthService {
    TokenInfo login(UserLoginDto userLoginDto);
    Member join(UserJoinDto userJoinDto) throws Exception;
    UserInfoDto info();
}