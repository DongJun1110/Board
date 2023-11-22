package umc.velog.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.velog.dto.user.UserJoinDto;
import umc.velog.security.TokenInfo;
import umc.velog.dto.user.UserInfoDto;
import umc.velog.dto.user.UserLoginDto;
import umc.velog.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody UserLoginDto userLoginDto) {
        return authService.login(userLoginDto);
    }

    @PostMapping("/register")
    public void register(@RequestBody UserJoinDto userJoinDto) {
        try {
            authService.join(userJoinDto);
        } catch (Exception e) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    @GetMapping("/info")
    public UserInfoDto info() {
        return authService.info();
    }
}