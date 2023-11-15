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
import umc.velog.domain.entity.Member;
import umc.velog.repository.MemberRepository;
import umc.velog.security.JwtTokenProvider;
import umc.velog.domain.entity.Role;
import umc.velog.security.SecurityUtil;
import umc.velog.security.TokenInfo;
import umc.velog.dto.user.UserInfoDto;
import umc.velog.dto.user.UserLoginDto;
import umc.velog.dto.user.UserJoinDto;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenInfo login(UserLoginDto userLoginDto) {
        Member member = memberRepository.findByUserId(userLoginDto.getUserId()).orElseThrow(() -> new UsernameNotFoundException("아이디 혹은 비밀번호를 확인하세요."));

        boolean matches = passwordEncoder.matches(userLoginDto.getPassword(), member.getPassword());
        if (!matches) throw new BadCredentialsException("아이디 혹은 비밀번호를 확인하세요.");

        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getUserId(), member.getPassword(), member.getAuthorities());

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        tokenInfo.setEmail(member.getUserId());
        tokenInfo.setMemberRole(member.getRole().toString());
        return tokenInfo;
    }

    @Override
    public Member join(UserJoinDto userJoinDto) throws Exception {

        String inputPassword = userJoinDto.getPassword();
        String inputConfirmPassword = userJoinDto.getConfirmPassword();

        if (!(isTwoPasswordAlign(inputPassword, inputConfirmPassword))) {
            throw new Exception();
        }

        Member member = new Member();
        member.setUserId(userJoinDto.getUserId());
        member.setPassword(passwordEncoder.encode(userJoinDto.getPassword()));
        if(userJoinDto.getRole().equals("ROLE_ADMIN")) {
            member.setRole(Role.ADMIN);
        }
        else {
            member.setRole(Role.USER);
        }
        member.setUsername(userJoinDto.getUserName());
        member.setEmail(userJoinDto.getEmail());
        member.setCreatedDate(new Date());
        return memberRepository.save(member);
    }

    @Override
    public UserInfoDto info() {
        UserInfoDto userInfoDto = SecurityUtil.getCurrentMemberId();
        return userInfoDto;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return  memberRepository.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Member member) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(member.getUsername())
                .password(passwordEncoder.encode(member.getPassword()))
                .roles(member.getRole().toString())
                .build();
    }

    private boolean isTwoPasswordAlign(String password, String confirmPassword) {
        if(password.equals(confirmPassword)){
            return true;
        }else return false;
    }
}