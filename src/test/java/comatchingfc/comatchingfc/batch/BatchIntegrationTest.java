package comatchingfc.comatchingfc.batch;

import comatchingfc.comatchingfc.batch.step.CheerPropensityStepConfig;
import comatchingfc.comatchingfc.batch.step.UserAiInfoStepConfig;
import comatchingfc.comatchingfc.batch.step.UserFeatureStepConfig;
import comatchingfc.comatchingfc.batch.step.UsersStepConfig;
import comatchingfc.comatchingfc.user.entity.*;
import comatchingfc.comatchingfc.user.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringBatchTest
@ActiveProfiles("test")
@Import({BatchConfig.class, CheerPropensityStepConfig.class, UserFeatureStepConfig.class,
        UserAiInfoStepConfig.class, UsersStepConfig.class})
class BatchIntegrationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private CheerPropensityRepository cheerPropensityRepository;

    @Autowired
    private UserFeatureRepository userFeatureRepository;

    @Autowired
    private UserAiInfoRepository userAiInfoRepository;

    @Autowired
    private UserRepository usersRepository;

    @BeforeEach
    void setUp() {
        // 테스트 데이터 삽입
        CheerPropensity cheer1 = CheerPropensity.builder().build();
        CheerPropensity cheer2 = CheerPropensity.builder().build();
        cheerPropensityRepository.saveAll(List.of(cheer1, cheer2));

        UserFeature feature1 = UserFeature.builder().build();
        UserFeature feature2 = UserFeature.builder().build();
        userFeatureRepository.saveAll(List.of(feature1, feature2));

        UserAiInfo aiInfo1 = UserAiInfo.builder().build();
        UserAiInfo aiInfo2 = UserAiInfo.builder().build();
        userAiInfoRepository.saveAll(List.of(aiInfo1, aiInfo2));

        Users user1 = Users.builder().build();
        Users user2 = Users.builder().build();
        usersRepository.saveAll(List.of(user1, user2));
    }

    @Test
    void testDeleteAllUsersJob() throws Exception {
        // JobParameters 설정
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        // Job 실행
        Job job = jobLauncherTestUtils.getJob();
        jobLauncherTestUtils.setJob(job);
        jobLauncherTestUtils.launchJob(jobParameters);

        // 삭제 확인
        assertThat(cheerPropensityRepository.count()).isEqualTo(0);
        assertThat(userFeatureRepository.count()).isEqualTo(0);
        assertThat(userAiInfoRepository.count()).isEqualTo(0);
        assertThat(usersRepository.count()).isEqualTo(0);
    }
}
