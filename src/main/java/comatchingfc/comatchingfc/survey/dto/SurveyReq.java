package comatchingfc.comatchingfc.survey.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class SurveyReq {
    @NotNull
    @NotEmpty
    private String questionContent;

    @NotNull
    @NotEmpty
    private List<String> choiceTexts;

    @NotNull
    @NotEmpty
    private List<Map<String, Integer>> choiceScores;
}
