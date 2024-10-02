package comatchingfc.comatchingfc.user.entity;

import comatchingfc.comatchingfc.user.enums.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAiFeature {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_ai_feature_id")
    private Long id;

    @OneToOne(mappedBy = "userAiFeature", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Users users;

    @Column(columnDefinition = "BINARY(16)")
    private byte[] uuid;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Gender targetGender;

    @Builder
    public UserAiFeature(byte[] uuid, Gender gender, Gender targetGender) {
        this.uuid = uuid;
        this.gender = gender;
        this.targetGender = targetGender;
    }

    public void setUsers(Users users) {
        this.users = users;
        if (users.getUserAiFeature() != this) {
            users.setUserAiFeature(this);
        }
    }

    public void setTargetGenderToMale() {
        this.targetGender = Gender.MALE;
    }

    public void setTargetGenderToFemale() {
        this.targetGender = Gender.FEMALE;
    }
}
