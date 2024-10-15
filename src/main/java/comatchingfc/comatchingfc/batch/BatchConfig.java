package comatchingfc.comatchingfc.batch;

import comatchingfc.comatchingfc.batch.step.*;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final JobLauncher jobLauncher;
    private final CheerPropensityStepConfig cheerPropensityStepConfig;
    private final UserFeatureStepConfig userFeatureStepConfig;
    private final UserAiInfoStepConfig userAiInfoStepConfig;
    private final UsersStepConfig usersStepConfig;
    private final MatchingHistoryStepConfig matchingHistoryStepConfig;

    @Bean
    public Job deleteAllUsersJob() {
        return new JobBuilder("deleteAllUsersJob", jobRepository)
                .start(cheerPropensityStepConfig.deleteCheerPropensityStep())
                .next(userFeatureStepConfig.deleteUserFeatureStep())
                .next(userAiInfoStepConfig.deleteUserAiInfoStep())
                .next(matchingHistoryStepConfig.deleteMatchingHistoryStep())
                .next(usersStepConfig.deleteUsersStep())
                .build();
    }

    // 스케줄링 설정: 매일 00시 30분에 배치 실행
//    @Scheduled(cron = "0 30 0 * * ?", zone = "Asia/Seoul")
    public void performDeleteAllUsersJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(deleteAllUsersJob(), jobParameters);
    }
}
