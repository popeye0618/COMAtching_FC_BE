package comatchingfc.comatchingfc.user.service;

import comatchingfc.comatchingfc.auth.jwt.refresh.service.RefreshTokenRedisService;
import comatchingfc.comatchingfc.user.dto.FeatureReq;
import comatchingfc.comatchingfc.user.dto.UserInfo;
import comatchingfc.comatchingfc.user.entity.UserFeature;
import comatchingfc.comatchingfc.user.entity.Users;
import comatchingfc.comatchingfc.user.enums.Gender;
import comatchingfc.comatchingfc.user.repository.UserRepository;
import comatchingfc.comatchingfc.utils.security.SecurityUtil;
import comatchingfc.comatchingfc.utils.uuid.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final SecurityUtil securityUtil;
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final UserRepository userRepository;

    public Long getParticipations() {
        return userRepository.count();
    }

    @Transactional
    public void updateFeature(FeatureReq featureReq) {
        Users user = securityUtil.getCurrentUserEntity();

        Gender gender = featureReq.getGender().equals(Gender.MALE.toString()) ? Gender.MALE : Gender.FEMALE;

        user.updateUsername(featureReq.getUsername());
        user.getUserAiInfo().getUserFeature().updateAge(featureReq.getAge());
        user.getUserAiInfo().getUserFeature().updateGender(gender);
        user.updateSocialId(featureReq.getSocialId());
        user.updateCheeringPlayer(featureReq.getCheeringPlayer());
    }

    public UserInfo getUserInfo() {
        Users user = securityUtil.getCurrentUserEntity();
        UserFeature userFeature = user.getUserAiInfo().getUserFeature();

        return UserInfo.builder()
                .username(user.getUsername())
                .age(userFeature.getAge())
                .gender(userFeature.getGender())
                .socialId(user.getSocialId())
                .cheeringPlayer(user.getCheeringPlayer())
                .cheerPropensity(userFeature.getCheerPropensityEnum())
                .build();
    }

    /**
     * 유저 비활성화
     * todo: 매치히스토리DB에서 뽑힌 기록 null 처리 로직 필요
     */
    @Transactional
    public void deactivateUser() {
        Users user = securityUtil.getCurrentUserEntity();
        user.deactivateUser();

        String userUuid = UUIDUtil.bytesToHex(user.getUserAiInfo().getUuid());
        refreshTokenRedisService.deleteRefreshToken(userUuid);
    }

    @Transactional
    public void logout() {
        Users user = securityUtil.getCurrentUserEntity();
        String userUuid = UUIDUtil.bytesToHex(user.getUserAiInfo().getUuid());
        refreshTokenRedisService.deleteRefreshToken(userUuid);
    }
}
