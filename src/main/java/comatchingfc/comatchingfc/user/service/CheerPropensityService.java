package comatchingfc.comatchingfc.user.service;

import comatchingfc.comatchingfc.auth.TokenUtil;
import comatchingfc.comatchingfc.auth.jwt.dto.TokenRes;
import comatchingfc.comatchingfc.player.dto.PlayerRes;
import comatchingfc.comatchingfc.player.service.PlayerService;
import comatchingfc.comatchingfc.user.dto.res.PropensityRes;
import comatchingfc.comatchingfc.user.dto.res.SavePropensityRes;
import comatchingfc.comatchingfc.user.dto.SurveyResult;
import comatchingfc.comatchingfc.user.entity.CheerPropensity;
import comatchingfc.comatchingfc.user.entity.UserFeature;
import comatchingfc.comatchingfc.user.entity.Users;
import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import comatchingfc.comatchingfc.user.enums.UserCrudType;
import comatchingfc.comatchingfc.user.repository.CheerPropensityRepository;
import comatchingfc.comatchingfc.utils.rabbitMQ.Message.req.UserCrudReqMsg;
import comatchingfc.comatchingfc.utils.rabbitMQ.UserRabbitMQUtil;
import comatchingfc.comatchingfc.utils.security.SecurityUtil;
import comatchingfc.comatchingfc.utils.uuid.UUIDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheerPropensityService {

    private final CheerPropensityRepository cheerPropensityRepository;
    private final SecurityUtil securityUtil;
    private final TokenUtil tokenUtil;
    private final PlayerService playerService;
    private final UserRabbitMQUtil userRabbitMQUtil;

    @Transactional
    public SavePropensityRes saveCheerPropensity(SurveyResult surveyResult) {
        Users user = securityUtil.getCurrentUserEntity();
        UserFeature userFeature = user.getUserAiInfo().getUserFeature();

        List<CheerPropensity> cheerPropensities = new ArrayList<>();

        CheerPropensity passionType = buildCheerPropensities(CheerPropensityEnum.열정형, surveyResult.getPassionType());
        passionType.setUserFeature(userFeature);
        cheerPropensities.add(passionType);

        CheerPropensity focusType = buildCheerPropensities(CheerPropensityEnum.집중형, surveyResult.getFocusType());
        focusType.setUserFeature(userFeature);
        cheerPropensities.add(focusType);

        CheerPropensity soccerNoviceType = buildCheerPropensities(CheerPropensityEnum.축린이형, surveyResult.getSoccerNoviceType());
        soccerNoviceType.setUserFeature(userFeature);
        cheerPropensities.add(soccerNoviceType);

        CheerPropensity soccerExpertType = buildCheerPropensities(CheerPropensityEnum.축잘알형, surveyResult.getSoccerExpertType());
        soccerExpertType.setUserFeature(userFeature);
        cheerPropensities.add(soccerExpertType);

        CheerPropensity mukbangType = buildCheerPropensities(CheerPropensityEnum.먹방형, surveyResult.getMukbangType());
        mukbangType.setUserFeature(userFeature);
        cheerPropensities.add(mukbangType);

        CheerPropensity socialType = buildCheerPropensities(CheerPropensityEnum.인싸형, surveyResult.getSocialType());
        socialType.setUserFeature(userFeature);
        cheerPropensities.add(socialType);

        // 쿼리 수 줄이기
        cheerPropensityRepository.saveAll(cheerPropensities);

        CheerPropensityEnum type = surveyResult.getHighestCheerPropensityType();
        userFeature.updateCheerPropensity(type);
        user.updateRoleToUser();

        String userUuid = UUIDUtil.bytesToHex(user.getUserAiInfo().getUuid());
        String userRole = user.getRole().toString();

        TokenRes tokenRes = tokenUtil.makeTokenRes(userUuid, userRole);
        List<PlayerRes> samePropensityPlayers = playerService.getSamePropensityPlayers(type);

        PropensityRes propensityRes = PropensityRes.builder()
                .cheerPropensity(type.getValue())
                .players(samePropensityPlayers)
                .build();

        UserCrudReqMsg userCrudReqMsg = userRabbitMQUtil.getUserCrudReqMsg(user);
        userRabbitMQUtil.requestUserToCsv(userCrudReqMsg, UserCrudType.CREATE);

        return new SavePropensityRes(propensityRes, tokenRes);
    }

    private CheerPropensity buildCheerPropensities(CheerPropensityEnum cheerPropensityEnum, int score) {
        return CheerPropensity.builder()
                .propensity(cheerPropensityEnum)
                .score(score)
                .build();
    }
}
