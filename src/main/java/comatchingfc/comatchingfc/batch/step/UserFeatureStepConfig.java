package comatchingfc.comatchingfc.batch.step;

import comatchingfc.comatchingfc.batch.tasklet.DeleteUserFeatureTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class UserFeatureStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DeleteUserFeatureTasklet deleteUserFeatureTasklet;

    @Bean
    public Step deleteUserFeatureStep() {
        return new StepBuilder("deleteUserFeatureStep", jobRepository)
                .tasklet(deleteUserFeatureTasklet, transactionManager)
                .build();
    }
}
