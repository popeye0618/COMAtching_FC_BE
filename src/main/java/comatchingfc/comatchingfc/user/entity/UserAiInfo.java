package comatchingfc.comatchingfc.user.entity;

import comatchingfc.comatchingfc.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAiInfo extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_ai_info_id")
    private Long id;

    @OneToOne(mappedBy = "userAiInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Users users;

    @OneToOne(mappedBy = "userFeatureAiInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private UserFeature userFeature;

    @Column(columnDefinition = "BINARY(16)")
    private byte[] uuid;

    private boolean isPicked = false;

    private boolean isPick = false;


    @Builder
    public UserAiInfo(byte[] uuid) {
        this.uuid = uuid;
    }

    public void setUsers(Users users) {
        this.users = users;
        if (users.getUserAiInfo() != this) {
            users.setUserAiInfo(this);
        }
    }

    public void setUserFeature(UserFeature userFeature) {
        this.userFeature = userFeature;
        if (userFeature.getUserFeatureAiInfo() != this) {
            userFeature.setUserAiInfo(this);
        }
    }
}
