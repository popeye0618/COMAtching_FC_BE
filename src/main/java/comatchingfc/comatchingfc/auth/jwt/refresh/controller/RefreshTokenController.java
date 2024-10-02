package comatchingfc.comatchingfc.auth.jwt.refresh.controller;

import comatchingfc.comatchingfc.auth.jwt.JwtUtil;
import comatchingfc.comatchingfc.auth.jwt.refresh.service.RefreshTokenService;
import comatchingfc.comatchingfc.utils.response.Response;
import comatchingfc.comatchingfc.utils.response.ResponseCode;
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
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/auth/refresh")
    public Response<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (refreshToken == null) {
            Response.errorResponse(ResponseCode.JWT_ERROR);
        }

        try {
            String uuid = jwtUtil.getUUID(refreshToken);
            String role = jwtUtil.getRole(refreshToken);

            String storedRefreshToken = refreshTokenService.getRefreshToken(uuid);

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

            refreshTokenService.saveRefreshToken(uuid, newRefreshToken);

            // Access Token을 HttpOnly 쿠키에 설정
            ResponseCookie accessCookie = ResponseCookie.from("accessToken", newAccessToken)
                    .httpOnly(true)
                    .secure(false) // HTTPS 환경에서만 전송하려면 true
                    .path("/")
                    .maxAge(60 * 60)
                    .sameSite("Strict")
                    .build();

            // Refresh Token을 HttpOnly 쿠키에 설정
            ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", newRefreshToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/auth/refresh")
                    .maxAge(24 * 60 * 60) // 1일
                    .sameSite("Strict")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
            response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

            return Response.ok();
        } catch (Exception e) {
            return Response.errorResponse(ResponseCode.JWT_ERROR);
        }
    }
}
