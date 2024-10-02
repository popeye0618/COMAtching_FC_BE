package comatchingfc.comatchingfc.user.entity;

import comatchingfc.comatchingfc.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_ai_feature_id", unique = true)
    private UserAiFeature userAiFeature;

    private String username;

    private int age;

    private String socialId;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_PENDING;

    private Boolean deactivated = false;

    @Builder
    public Users(String username, int age, String socialId) {
        this.username = username;
        this.age = age;
        this.socialId = socialId;
    }

    public void setUserAiFeature(UserAiFeature userAiFeature) {
        this.userAiFeature = userAiFeature;
        if (userAiFeature.getUsers() != this) {
            userAiFeature.setUsers(this);
        }
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateAge(int age) {
        this.age = age;
    }

    public void setRoleToUser() {
        this.role = UserRole.ROLE_USER;
    }

    public void deactivateUser() {
        this.deactivated = true;
    }

    public void activateUser() {
        this.deactivated = false;
    }
}
