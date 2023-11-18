package umc.velog.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String email;
    private String memberRole;

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }
}