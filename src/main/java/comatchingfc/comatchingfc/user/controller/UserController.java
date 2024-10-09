package comatchingfc.comatchingfc.user.controller;

import comatchingfc.comatchingfc.auth.jwt.dto.TokenRes;
import comatchingfc.comatchingfc.user.dto.*;
import comatchingfc.comatchingfc.user.service.CheerPropensityService;
import comatchingfc.comatchingfc.user.service.UserService;
import comatchingfc.comatchingfc.utils.response.Response;
import comatchingfc.comatchingfc.utils.security.SecurityUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CheerPropensityService cheerPropensityService;
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
    // todo: 매칭 여부 판별 + 매칭 된 상대 정보 넣는 로직 추가 필요
    @GetMapping("/auth/user/info")
    public Response<UserInfoRes> getUserInfo() {
        UserInfo myInfo = userService.getUserInfo();
        //todo : 매칭 했는지 판별 + 매칭된 상대 정보 UserInfo dto에 담기
        // 아래는 매칭은 안한 사람인 경우 상대 정보 null
        UserInfoRes userInfoRes = UserInfoRes.builder()
                .myInfo(myInfo)
                .enemyInfo(null)
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
}
