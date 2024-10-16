package comatchingfc.comatchingfc.utils.security;

import comatchingfc.comatchingfc.admin.entity.Admin;
import comatchingfc.comatchingfc.admin.repository.AdminRepository;
import comatchingfc.comatchingfc.auth.jwt.dto.admin.CustomAdmin;
import comatchingfc.comatchingfc.auth.jwt.dto.user.CustomUser;
import comatchingfc.comatchingfc.exception.BusinessException;
import comatchingfc.comatchingfc.user.entity.Users;
import comatchingfc.comatchingfc.user.repository.UserRepository;
import comatchingfc.comatchingfc.utils.response.ResponseCode;
import comatchingfc.comatchingfc.utils.uuid.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            if (authentication.getPrincipal() instanceof CustomUser customUser) {
                return customUser.getRole();
            } else if (authentication.getPrincipal() instanceof CustomAdmin customAdmin) {
                return customAdmin.getRole();
            }
        }
        throw new BusinessException(ResponseCode.USER_NOT_FOUND);
    }

    /**
     * 현재 인증된 일반 사용자 정보를 반환
     *
     * @return 현재 인증된 User 엔티티
     */
    public Users getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUser customUser) {
            String uuid = customUser.getUuid();

            return userRepository.findUsersByUuid(UUIDUtil.uuidStringToBytes(uuid))
                    .orElseThrow(() -> new BusinessException(ResponseCode.USER_NOT_FOUND));
        }

        throw new BusinessException(ResponseCode.USER_NOT_FOUND);
    }

    /**
     * 현재 인증된 Admin 사용자 정보를 반환
     *
     * @return 현재 인증된 Admin 엔티티
     */
    public Admin getCurrentAdminEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomAdmin customAdmin) {
            String uuid = customAdmin.getUuid();

            return adminRepository.findByUuid(UUIDUtil.uuidStringToBytes(uuid))
                    .orElseThrow(() -> new BusinessException(ResponseCode.USER_NOT_FOUND));
        }

        throw new BusinessException(ResponseCode.USER_NOT_FOUND);
    }


    public ResponseCookie setRefreshResponseCookie(String refreshToken) {
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/auth/refresh")
//                .domain(".comatching.site")
                .maxAge(24 * 60 * 60) // 1일
                .sameSite("Strict")
                .build();
        return refreshCookie;
    }

    public ResponseCookie setAccessResponseCookie(String accessToken) {
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(true) // HTTPS 환경에서만 전송하려면 true
                .path("/")
//                .domain(".comatching.site")
                .maxAge(60 * 60) // 1시간
                .sameSite("Strict")
                .build();
        return accessCookie;
    }

    public ResponseCookie deleteAccessResponseCookie() {
        return ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
//                .domain(".comatching.site")
                .maxAge(0) // 쿠키 삭제
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie deleteRefreshResponseCookie() {
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
//                .domain(".comatching.site")
                .path("/auth/refresh")
                .maxAge(0) // 쿠키 삭제
                .sameSite("Strict")
                .build();
    }

}
