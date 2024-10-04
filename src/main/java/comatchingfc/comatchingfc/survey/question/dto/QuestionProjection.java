package comatchingfc.comatchingfc.survey.question.dto;

import comatchingfc.comatchingfc.survey.choice.dto.ChoiceProjection;

import java.util.List;

public interface QuestionProjection {
    String getContent();
    List<ChoiceProjection> getChoices();
}
