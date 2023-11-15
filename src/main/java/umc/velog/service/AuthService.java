package umc.velog.service;

import umc.velog.domain.entity.Member;
import umc.velog.security.TokenInfo;
import umc.velog.dto.UserInfoDto;
import umc.velog.dto.UserJoinDto;
import umc.velog.dto.UserLoginDto;

public interface AuthService {
    TokenInfo login(UserLoginDto userLoginDto);
    Member join(UserJoinDto userJoinDto) throws Exception;
    UserInfoDto info();
}