package comatchingfc.comatchingfc.user.entity;

import comatchingfc.comatchingfc.user.enums.Gender;
import comatchingfc.comatchingfc.user.enums.UserRole;
import comatchingfc.comatchingfc.user.repository.UserAiFeatureRepository;
import comatchingfc.comatchingfc.user.repository.UserRepository;
import comatchingfc.comatchingfc.utils.UUIDUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test") // "test" 프로파일 활성화
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 내장 데이터베이스 사용 설정
class UsersTest {

    @Autowired private UserRepository userRepository;
    @Autowired private UserAiFeatureRepository userAiFeatureRepository;

    @Test
    @DisplayName("User 엔티티 생성 및 기본 필드 검증")
    void testUserEntityCreation() {
        //given
        String username = "천승환";
        int age = 25;
        String socialId = "tmdghkkks";

        //when
        Users users = Users.builder()
                .username(username)
                .age(age)
                .socialId(socialId)
                .build();
        //then
        assertThat(users.getUsername()).isEqualTo(username);
        assertThat(users.getAge()).isEqualTo(age);
        assertThat(users.getSocialId()).isEqualTo(socialId);
        assertThat(users.getRole()).isEqualTo(UserRole.ROLE_PENDING);
        assertThat(users.getDeactivated()).isFalse();
    }

    @Test
    @DisplayName("User와 UserAiFeature의 OneToOne 연관관계 검증")
    void testUserAndFeatureRelationship() {
        //given
        Users users = Users.builder()
                .username("천승환")
                .age(25)
                .socialId("tmdghkkks")
                .build();

        UserAiFeature userAiFeature = UserAiFeature.builder()
                .uuid(UUIDUtil.createUUID())
                .gender(Gender.MALE)
                .targetGender(Gender.FEMALE)
                .build();

        users.setUserAiFeature(userAiFeature);
        userAiFeature.setUsers(users);

        userRepository.save(users);
        userAiFeatureRepository.save(userAiFeature);

        //when
        Optional<Users> userOpt = userRepository.findById(users.getId());
        Optional<UserAiFeature> userFeatureOpt = userAiFeatureRepository.findById(userAiFeature.getId());

        //then
        assertThat(userOpt).isPresent();
        assertThat(userFeatureOpt).isPresent();

        Users retrievedUsers = userOpt.get();
        UserAiFeature retrievedUserFeature = userFeatureOpt.get();

        assertThat(retrievedUsers.getUserAiFeature()).isNotNull();
        assertThat(retrievedUsers.getUserAiFeature().getId()).isEqualTo(userAiFeature.getId());

        assertThat(retrievedUserFeature.getUsers()).isNotNull();
        assertThat(retrievedUserFeature.getUsers().getId()).isEqualTo(users.getId());
    }
}