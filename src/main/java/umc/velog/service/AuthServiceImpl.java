package umc.velog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umc.velog.security.JwtTokenProvider;
import umc.velog.security.Role;
import umc.velog.security.SecurityUtil;
import umc.velog.security.TokenInfo;
import umc.velog.domain.entity.User;
import umc.velog.dto.user.UserInfoDto;
import umc.velog.dto.user.UserJoinDto;
import umc.velog.dto.user.UserLoginDto;
import umc.velog.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenInfo login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUserId(userLoginDto.getUserId()).orElseThrow(() -> new UsernameNotFoundException("아이디 혹은 비밀번호를 확인하세요."));

        boolean matches = passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword());
        if (!matches) throw new BadCredentialsException("아이디 혹은 비밀번호를 확인하세요.");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword(), user.getAuthorities());

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        tokenInfo.setEmail(user.getUserId());
        tokenInfo.setMemberRole(user.getRole().toString());
        return tokenInfo;
    }

    @Override
    public void join(UserJoinDto userJoinDto) {
        User user = new User();
        user.setUserId(userJoinDto.getUserId());
        user.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
        if(userJoinDto.getRole().equals("ROLE_ADMIN")) {
            user.setRole(Role.ADMIN);
        }
        else {
            user.setRole(Role.USER);
        }
        user.setUserName(userJoinDto.getUserName());
        userRepository.save(user);
    }

    @Override
    public UserInfoDto info() {
        UserInfoDto userInfoDto = SecurityUtil.getCurrentMemberId();
        return userInfoDto;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRole().toString())
                .build();
    }
}