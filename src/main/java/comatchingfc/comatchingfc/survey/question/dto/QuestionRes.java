package comatchingfc.comatchingfc.survey.question.dto;

import comatchingfc.comatchingfc.survey.choice.dto.ChoiceRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRes {
    private String content;
    private List<ChoiceRes> choices;
}
