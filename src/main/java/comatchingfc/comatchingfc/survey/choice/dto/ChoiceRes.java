package comatchingfc.comatchingfc.survey.choice.dto;

import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceRes {
    private String choiceText;
    private Map<CheerPropensityEnum, Integer> cheerPropensities;
}
