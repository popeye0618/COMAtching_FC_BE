package comatchingfc.comatchingfc.admin.controller;

import comatchingfc.comatchingfc.admin.dto.AdminLoginReq;
import comatchingfc.comatchingfc.admin.dto.AdminRegisterReq;
import comatchingfc.comatchingfc.admin.service.AdminService;
import comatchingfc.comatchingfc.auth.jwt.dto.TokenRes;
import comatchingfc.comatchingfc.utils.response.Response;
import comatchingfc.comatchingfc.utils.security.SecurityUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final SecurityUtil securityUtil;

    @PostMapping("/admin/register")
    public Response<Void> adminRegister(@RequestBody @Valid AdminRegisterReq registerReq) {
        adminService.adminRegister(registerReq);
        return Response.ok();
    }

    @GetMapping("/admin/check/{accountId}")
    public Response<Boolean> checkAdminDuplicate(@PathVariable String accountId) {
        return Response.ok(!adminService.isAccountDuplicated(accountId));
    }

    @PostMapping("/admin/login")
    public Response<Void> adminLogin(@RequestBody @Valid AdminLoginReq loginReq, HttpServletResponse response) {
        TokenRes tokenRes = adminService.adminLogin(loginReq);

        response.addHeader("Authorization", "Bearer " + tokenRes.getAccessToken());
        response.addHeader("Refresh-Token", tokenRes.getRefreshToken());

        return Response.ok();
    }

    @GetMapping("/auth/admin/activate/{ticket}")
    public Response<Void> activateUser(@PathVariable String ticket) {
        adminService.activateUser(ticket);
        return Response.ok();
    }
}
