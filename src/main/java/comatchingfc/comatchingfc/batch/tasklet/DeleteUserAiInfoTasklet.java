package comatchingfc.comatchingfc.batch.tasklet;

import comatchingfc.comatchingfc.user.repository.UserAiInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUserAiInfoTasklet implements Tasklet {

    private final UserAiInfoRepository userAiInfoRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        try {
            long count = userAiInfoRepository.count();
            userAiInfoRepository.deleteAll();
            System.out.println("삭제된 UserAiInfo 수: " + count);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return RepeatStatus.FINISHED;
    }
}
