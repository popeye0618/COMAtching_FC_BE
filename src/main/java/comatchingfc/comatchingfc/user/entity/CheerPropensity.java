package comatchingfc.comatchingfc.user.entity;

import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import comatchingfc.comatchingfc.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheerPropensity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cheer_propensity_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_feature_id")
    private UserFeature userFeature;

    @Enumerated(EnumType.STRING)
    private CheerPropensityEnum cheerPropensityEnum;

    private int score;

    @Builder
    public CheerPropensity(CheerPropensityEnum propensity, int score) {
        this.cheerPropensityEnum = propensity;
        this.score = score;
    }

    public void updateScore(int score) {
        this.score = score;
    }

    public void setUserFeature(UserFeature userFeature) {
        this.userFeature = userFeature;
        if (userFeature != null && !userFeature.getCheerPropensities().contains(this)) {
            userFeature.addCheerPropensity(this);
        }
    }
}
