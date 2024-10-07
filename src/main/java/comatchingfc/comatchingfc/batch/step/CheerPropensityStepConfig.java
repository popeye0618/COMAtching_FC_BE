package comatchingfc.comatchingfc.batch.step;

import comatchingfc.comatchingfc.batch.tasklet.DeleteCheerPropensityTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class CheerPropensityStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DeleteCheerPropensityTasklet deleteCheerPropensityTasklet;

    @Bean
    public Step deleteCheerPropensityStep() {
        return new StepBuilder("deleteCheerPropensityStep", jobRepository)
                .tasklet(deleteCheerPropensityTasklet, transactionManager)
                .build();
    }
}
