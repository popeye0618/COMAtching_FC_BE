package comatchingfc.comatchingfc.user.entity;

import comatchingfc.comatchingfc.user.enums.Gender;
import comatchingfc.comatchingfc.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
    private UserAiInfo userAiInfo;

    @OneToMany(mappedBy = "userFeature", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheerPropensity> cheerPropensities = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int age;
}
