package comatchingfc.comatchingfc.auth;

import comatchingfc.comatchingfc.auth.jwt.JwtUtil;
import comatchingfc.comatchingfc.auth.jwt.dto.TokenRes;
import comatchingfc.comatchingfc.auth.jwt.refresh.service.RefreshTokenRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenUtil {

    private final RefreshTokenRedisService refreshTokenRedisService;
    private final JwtUtil jwtUtil;

    public TokenRes makeTokenRes(String userUuid, String userRole) {
        String accessToken = jwtUtil.generateAccessToken(userUuid, userRole);
        String refreshToken = refreshTokenRedisService.getRefreshToken(userUuid);

        if (refreshToken == null) {
            refreshToken = jwtUtil.generateRefreshToken(userUuid, userRole);
            refreshTokenRedisService.saveRefreshToken(userUuid, refreshToken);
        }

        return TokenRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
