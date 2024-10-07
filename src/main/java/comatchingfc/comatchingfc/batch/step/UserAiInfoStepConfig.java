package comatchingfc.comatchingfc.batch.step;

import comatchingfc.comatchingfc.batch.tasklet.DeleteUserAiInfoTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class UserAiInfoStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DeleteUserAiInfoTasklet deleteUserAiInfoTasklet;

    @Bean
    public Step deleteUserAiInfoStep() {
        return new StepBuilder("deleteUserAiInfoStep", jobRepository)
                .tasklet(deleteUserAiInfoTasklet, transactionManager)
                .build();
    }
}
