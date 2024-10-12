package comatchingfc.comatchingfc.user.entity;

import java.util.List;

import comatchingfc.comatchingfc.admin.entity.UserNoticeCheck;
import comatchingfc.comatchingfc.match.entity.MatchingHistory;
import comatchingfc.comatchingfc.user.enums.UserRole;
import comatchingfc.comatchingfc.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_ai_info_id", unique = true)
    private UserAiInfo userAiInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "matching_history_id")
    private MatchingHistory matchingHistory;

    @OneToMany(mappedBy = "user")
    private List<UserNoticeCheck> userNoticeCheckList;

    private String identifyKey;

    private String username;

    private String socialId;

    private String cheeringPlayer;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_PENDING;

    private Boolean deactivated = false;

    @Builder
    public Users(String identifyKey, String username, String socialId, String cheeringPlayer) {
        this.identifyKey = identifyKey;
        this.username = username;
        this.socialId = socialId;
        this.cheeringPlayer = cheeringPlayer;
    }

    public void setUserAiInfo(UserAiInfo userAiInfo) {
        this.userAiInfo = userAiInfo;
        if (userAiInfo.getUsers() != this) {
            userAiInfo.setUsers(this);
        }
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateSocialId(String socialId) {
        this.socialId = socialId;
    }

    public void updateCheeringPlayer(String cheeringPlayer) {
        this.cheeringPlayer = cheeringPlayer;
    }

    public void updateRoleToUser() {
        this.role = UserRole.ROLE_USER;
    }

    public void deactivateUser() {
        this.deactivated = true;
    }

    public void activateUser() {
        this.deactivated = false;
    }

    public void addMatchingHistory(MatchingHistory matchingHistory) {
        this.matchingHistory = matchingHistory;
        if (matchingHistory.getApplier() != null && matchingHistory.getApplier() != this) {
            matchingHistory.getApplier().addMatchingHistory(matchingHistory);
        }
        if (matchingHistory.getEnemy() != null && matchingHistory.getEnemy() != this) {
            matchingHistory.getEnemy().addMatchingHistory(matchingHistory);
        }
    }
}
