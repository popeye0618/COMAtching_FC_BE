package comatchingfc.comatchingfc.auth.controller;

import comatchingfc.comatchingfc.auth.dto.Res.UserLoginRes;
import comatchingfc.comatchingfc.auth.dto.req.UserLoginReq;
import comatchingfc.comatchingfc.auth.jwt.dto.TokenRes;
import comatchingfc.comatchingfc.auth.service.AuthService;
import comatchingfc.comatchingfc.utils.response.Response;
import comatchingfc.comatchingfc.utils.security.SecurityUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final SecurityUtil securityUtil;

    @PostMapping("/user/login")
    public Response<String> userLogin(@RequestBody @Valid UserLoginReq userLoginReq, HttpServletResponse response) {


        UserLoginRes userLoginRes = authService.userLogin(userLoginReq);
        TokenRes tokenRes = userLoginRes.getTokenRes();

        ResponseCookie accessCookie = securityUtil.setAccessResponseCookie(tokenRes.getAccessToken());
        ResponseCookie refreshCookie = securityUtil.setRefreshResponseCookie(tokenRes.getRefreshToken());

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

//        return Response.ok(userLoginRes);
        return Response.ok(userLoginRes.getRole());
    }

    @GetMapping("/check-role")
    public Response<String> checkRole() {
        String role = securityUtil.getRole();
        return Response.ok(role);
    }
}
