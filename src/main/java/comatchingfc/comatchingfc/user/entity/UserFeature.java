package comatchingfc.comatchingfc.user.entity;

import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import comatchingfc.comatchingfc.user.enums.Gender;
import comatchingfc.comatchingfc.user.enums.TeamSide;
import comatchingfc.comatchingfc.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFeature extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_feature_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_ai_info_id", unique = true)
    private UserAiInfo userFeatureAiInfo;

    @OneToMany(mappedBy = "userFeature", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheerPropensity> cheerPropensities = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int age;

    @Enumerated(EnumType.STRING)
    private CheerPropensityEnum propensity;

    @Enumerated(EnumType.STRING)
    private TeamSide teamSide;

    @Builder
    public UserFeature(Gender gender, int age) {
        this.gender = gender;
        this.age = age;
    }

    public void setUserAiInfo(UserAiInfo userAiInfo) {
        this.userFeatureAiInfo = userAiInfo;
        if (userAiInfo.getUserFeature() != this) {
            userAiInfo.setUserFeature(this);
        }
    }

    public void addCheerPropensity(CheerPropensity cheerPropensity) {
        cheerPropensities.add(cheerPropensity);
        if (cheerPropensity.getUserFeature() != this) {
            cheerPropensity.setUserFeature(this);
        }
    }

    public void removeCheerPropensity(CheerPropensity cheerPropensity) {
        cheerPropensities.remove(cheerPropensity);
        if (cheerPropensity.getUserFeature() == this) {
            cheerPropensity.setUserFeature(null);
        }
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

    public void updateAge(int age) {
        this.age = age;
    }

    public void updateCheerPropensity(CheerPropensityEnum propensity) {
        this.propensity = propensity;
    }
}
