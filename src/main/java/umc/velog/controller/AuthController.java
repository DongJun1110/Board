package umc.velog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umc.velog.security.TokenInfo;
import umc.velog.dto.user.UserInfoDto;
import umc.velog.dto.user.UserJoinDto;
import umc.velog.dto.user.UserLoginDto;
import umc.velog.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

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
        authService.join(userJoinDto);
    }

    @GetMapping("/info")
    public UserInfoDto info() {
        return authService.info();
    }
}