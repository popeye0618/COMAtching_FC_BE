package comatchingfc.comatchingfc.auth.jwt.refresh.controller;

import comatchingfc.comatchingfc.auth.jwt.JwtUtil;
import comatchingfc.comatchingfc.auth.jwt.refresh.service.RefreshTokenRedisService;
import comatchingfc.comatchingfc.utils.response.Response;
import comatchingfc.comatchingfc.utils.response.ResponseCode;
import comatchingfc.comatchingfc.utils.security.SecurityUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final SecurityUtil securityUtil;

    @PostMapping("/auth/refresh")
    public Response<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader("Refresh-Token");

        if (refreshToken == null) {
            Response.errorResponse(ResponseCode.JWT_ERROR);
        }

        try {
            String uuid = jwtUtil.getUUID(refreshToken);
            String role = jwtUtil.getRole(refreshToken);

            String storedRefreshToken = refreshTokenRedisService.getRefreshToken(uuid);

            if (storedRefreshToken == null) {
                return Response.errorResponse(ResponseCode.JWT_ERROR);
            }

            // 리프레시 토큰 일치 여부 및 만료 여부 검증
            if (jwtUtil.isExpired(refreshToken)) {
                return Response.errorResponse(ResponseCode.JWT_ERROR);
            }

            if (!refreshToken.equals(storedRefreshToken)) {
                return Response.errorResponse(ResponseCode.JWT_ERROR);
            }

            String newAccessToken = jwtUtil.generateAccessToken(uuid, role);
            String newRefreshToken = jwtUtil.generateRefreshToken(uuid, role);

            refreshTokenRedisService.saveRefreshToken(uuid, newRefreshToken);

            response.addHeader("Authorization", "Bearer " + newAccessToken);
            response.addHeader("Refresh-Token", newRefreshToken);

            return Response.ok();
        } catch (Exception e) {
            return Response.errorResponse(ResponseCode.JWT_ERROR);
        }
    }
}
