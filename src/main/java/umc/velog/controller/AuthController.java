package umc.velog.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umc.velog.security.TokenInfo;
import umc.velog.dto.UserInfoDto;
import umc.velog.dto.UserJoinDto;
import umc.velog.dto.UserLoginDto;
import umc.velog.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/loginPage")
    public String loginPage() {
        return "auth/loginForm";
    }

    @GetMapping("/joinPage")
    public String joinPage() {
        return "auth/joinForm";
    }

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