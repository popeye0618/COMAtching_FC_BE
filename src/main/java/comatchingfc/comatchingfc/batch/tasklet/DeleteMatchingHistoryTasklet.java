package comatchingfc.comatchingfc.batch.tasklet;

import comatchingfc.comatchingfc.match.repository.MatchingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteMatchingHistoryTasklet implements Tasklet {

    private final MatchingHistoryRepository matchingHistoryRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        try {
            long count = matchingHistoryRepository.count();
            matchingHistoryRepository.deleteAll();
            System.out.println("삭제된 MatchingHistory 수: " + count);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return RepeatStatus.FINISHED;
    }
}
