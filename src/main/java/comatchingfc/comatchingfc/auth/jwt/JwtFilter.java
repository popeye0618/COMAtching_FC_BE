package comatchingfc.comatchingfc.auth.jwt;

import comatchingfc.comatchingfc.auth.jwt.dto.CustomUser;
import comatchingfc.comatchingfc.auth.jwt.dto.UserDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private static final List<String> WHITELIST = List.of(
            "/auth/refresh",
            "/admin/register",
            "/admin/login",
            "/user/login",
            "/test/match"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        log.info("요청 URL = {}", requestURI);
        if (WHITELIST.stream().anyMatch(requestURI::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        setSecurityHeaders(response);
        String accessToken = getAccessToken(request);

        try {
            if (accessToken != null && !jwtUtil.isExpired(accessToken)) {
                log.info("엑세스 토큰 유효");
                setAuthentication(accessToken);
                filterChain.doFilter(request, response);
                return;
            }
        } catch (ExpiredJwtException e) {
            log.info("엑세스 토큰 만료");
            throw new JwtException("TOKEN_EXPIRED");
        } catch (SignatureException e) {
            log.info("엑세스 토큰 무결성 오류");
            throw new JwtException("TOKEN_INVALID");
        } catch (JwtException e) {
            log.info("엑세스 토큰 오류");
            throw new JwtException("TOKEN_INVALID");
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        String uuid = jwtUtil.getUUID(accessToken);
        String role = jwtUtil.getRole(accessToken);

        UserDto userDto = new UserDto();
        userDto.setUuid(uuid);
        userDto.setRole(role);

        CustomUser customUser = new CustomUser(userDto);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String getTokenFromCookies(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    private String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            log.info("엑세스 토큰 출력 = {}", bearerToken.substring(7));
            return bearerToken.substring(7);
        }

        return null;
    }

    private void setSecurityHeaders(HttpServletResponse response) {
        response.setHeader("X-Content-Type-Options", "nosniff"); //브라우저가 MIME 타입을 스니핑하지 못하도록 설정
        response.setHeader("X-Frame-Options", "DENY"); //페이지가 iframe 또는 프레임에 삽입되지 않도록 설정하여 Clickjacking 공격을 방지
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains"); //모든 연결이 HTTPS를 통해 이루어지도록 강제
        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self' https://trusted-cdn.com");
    }
}
