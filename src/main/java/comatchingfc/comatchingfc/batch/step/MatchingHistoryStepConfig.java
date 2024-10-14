package comatchingfc.comatchingfc.batch.step;

import comatchingfc.comatchingfc.batch.tasklet.DeleteMatchingHistoryTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class MatchingHistoryStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DeleteMatchingHistoryTasklet deleteMatchingHistoryTasklet;

    @Bean
    public Step deleteMatchingHistoryStep() {
        return new StepBuilder("deleteMatchingHistoryStep", jobRepository)
                .tasklet(deleteMatchingHistoryTasklet, transactionManager)
                .build();
    }

}
