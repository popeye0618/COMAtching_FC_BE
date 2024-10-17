package comatchingfc.comatchingfc.user.controller;

import java.util.List;

import comatchingfc.comatchingfc.auth.jwt.dto.TokenRes;
import comatchingfc.comatchingfc.user.dto.*;
import comatchingfc.comatchingfc.user.dto.req.FeatureReq;
import comatchingfc.comatchingfc.user.dto.res.PropensityRes;
import comatchingfc.comatchingfc.user.dto.res.SavePropensityRes;
import comatchingfc.comatchingfc.user.dto.res.UserNoticeRes;
import comatchingfc.comatchingfc.user.service.CheerPropensityService;
import comatchingfc.comatchingfc.user.service.UserNoticeService;
import comatchingfc.comatchingfc.user.service.UserService;
import comatchingfc.comatchingfc.utils.response.Response;
import comatchingfc.comatchingfc.utils.security.SecurityUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CheerPropensityService cheerPropensityService;
    private final UserNoticeService userNoticeService;
    private final SecurityUtil securityUtil;

    @GetMapping("/api/participations")
    public Response<Long> getParticipations() {
        return Response.ok(userService.getParticipations());
    }

    /**
     * 유저 정보 입력
     * @param featureReq 유저 정보
     */
    @PostMapping("/auth/pending/feature")
    public Response<Void> inputFeature(@RequestBody @Valid FeatureReq featureReq) {
        userService.updateFeature(featureReq);
        return Response.ok();
    }

    /**
     * 설문 결과 저장 메소드
     * @param surveyResult 설문 결과
     * @param response Role이 바뀐 토큰 제공
     */
    @PostMapping("/auth/pending/survey")
    public Response<PropensityRes> saveCheerPropensity(@RequestBody SurveyResult surveyResult, HttpServletResponse response) {
        SavePropensityRes savePropensityRes = cheerPropensityService.saveCheerPropensity(surveyResult);

        TokenRes tokenRes = savePropensityRes.getTokenRes();
        response.addHeader("Set-Cookie", securityUtil.setAccessResponseCookie(tokenRes.getAccessToken()).toString());
        response.addHeader("Set-Cookie", securityUtil.setRefreshResponseCookie(tokenRes.getRefreshToken()).toString());

        return Response.ok(savePropensityRes.getPropensityRes());
    }

    /**
     * 유저 정보 수정
     * @param featureReq 수정된 유저 정보
     */
    @PatchMapping("/auth/user/feature")
    public Response<Void> updateUserFeature(@RequestBody @Valid FeatureReq featureReq) {
        userService.updateFeature(featureReq);
        return Response.ok();
    }

    /**
     * 메인페이지 Get 메소드
     * @return 내 정보, 매칭 상대 정보 (매칭 하지 않았으면 null)
     */
    @GetMapping("/auth/user/info")
    public Response<UserInfoRes> getUserInfo() {
        UserInfo myInfo = userService.getUserInfo();
        UserInfo enemyUserInfo = null;
        if (userService.userMatched()) {
            enemyUserInfo = userService.getEnemyUserInfo();

        }
        // 아래는 매칭은 안한 사람인 경우 상대 정보 null
        UserInfoRes userInfoRes = UserInfoRes.builder()
                .myInfo(myInfo)
                .enemyInfo(enemyUserInfo)
                .build();
        return Response.ok(userInfoRes);
    }

    /**
     * 유저 탈퇴 (비활성화)
     */
    @GetMapping("/auth/user/quit")
    public Response<Void> deactivateUser(HttpServletResponse response) {
        userService.deactivateUser();

        response.addHeader("Set-Cookie", securityUtil.deleteAccessResponseCookie().toString());
        response.addHeader("Set-Cookie", securityUtil.deleteRefreshResponseCookie().toString());

        return Response.ok();
    }

    @GetMapping("/auth/user/logout")
    public Response<Void> logoutUser(HttpServletResponse response) {
        userService.logout();

        response.addHeader("Set-Cookie", securityUtil.deleteAccessResponseCookie().toString());
        response.addHeader("Set-Cookie", securityUtil.deleteRefreshResponseCookie().toString());

        return Response.ok();
    }

    @GetMapping("/auth/user/api/inquiry/notice")
    public Response<List<UserNoticeRes>> inquiryNotice(){
        return Response.ok(userNoticeService.inquiryUserNotice());
    }
}
