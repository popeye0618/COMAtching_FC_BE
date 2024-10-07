package comatchingfc.comatchingfc.batch.step;

import comatchingfc.comatchingfc.batch.tasklet.DeleteUsersTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class UsersStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DeleteUsersTasklet deleteUsersTasklet;

    @Bean
    public Step deleteUsersStep() {
        return new StepBuilder("deleteUsersStep", jobRepository)
                .tasklet(deleteUsersTasklet, transactionManager)
                .build();
    }
}
