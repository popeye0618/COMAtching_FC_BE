package comatchingfc.comatchingfc.admin.controller;

import comatchingfc.comatchingfc.admin.dto.req.AdminRegisterReq;
import comatchingfc.comatchingfc.admin.service.AdminService;
import comatchingfc.comatchingfc.player.dto.PlayerInfoReq;
import comatchingfc.comatchingfc.player.service.PlayerService;
import comatchingfc.comatchingfc.utils.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final PlayerService playerService;

    @PostMapping("/admin/register")
    public Response<Void> adminRegister(@RequestBody @Valid AdminRegisterReq registerReq) {
        adminService.adminRegister(registerReq);
        return Response.ok();
    }

    @GetMapping("/admin/check/{accountId}")
    public Response<Boolean> checkAdminDuplicate(@PathVariable String accountId) {
        return Response.ok(!adminService.isAccountDuplicated(accountId));
    }

    @GetMapping("/auth/admin/activate/{ticket}")
    public Response<Void> activateUser(@PathVariable String ticket) {
        adminService.activateUser(ticket);
        return Response.ok();
    }

    @PostMapping("/auth/admin/player")
    public Response<Void> addPlayer(@RequestBody @Valid PlayerInfoReq playerInfoReq) {
        playerService.addPlayer(playerInfoReq);

        return Response.ok();
    }

}
