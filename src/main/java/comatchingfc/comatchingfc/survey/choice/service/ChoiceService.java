package comatchingfc.comatchingfc.survey.choice.service;

import comatchingfc.comatchingfc.exception.BusinessException;
import comatchingfc.comatchingfc.survey.choice.entity.Choice;
import comatchingfc.comatchingfc.survey.choice.entity.ChoiceCheerPropensity;
import comatchingfc.comatchingfc.survey.choice.repository.ChoiceRepository;
import comatchingfc.comatchingfc.survey.question.entity.Question;
import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import comatchingfc.comatchingfc.utils.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChoiceService {

    private final ChoiceRepository choiceRepository;

    @Transactional
    public void saveChoiceWithScore(Question question, List<String> choiceTexts, List<Map<String, Integer>> choiceScores) {

        if (choiceTexts.size() != choiceScores.size()) {
            throw new BusinessException(ResponseCode.BAD_REQUEST);
        }

        List<Choice> choices = new ArrayList<>();

        for (int i = 0; i < choiceTexts.size(); i++) {
            String choiceText = choiceTexts.get(i);
            Map<String, Integer> scoresMap = choiceScores.get(i);

            Choice choice = Choice.builder()
                    .choiceText(choiceText)
                    .question(question)
                    .build();

            // 점수 매핑
            scoresMap.forEach((key, value) -> {
                CheerPropensityEnum propensityEnum;
                try {
                    propensityEnum = CheerPropensityEnum.valueOf(key);
                } catch (IllegalArgumentException e) {
                    throw new BusinessException(ResponseCode.BAD_REQUEST);
                }

                ChoiceCheerPropensity cheerPropensity = ChoiceCheerPropensity.builder()
                        .cheerPropensityEnum(propensityEnum)
                        .score(value)
                        .build();

                choice.addCheerPropensity(cheerPropensity);
            });

            choices.add(choice);
        }

        choiceRepository.saveAll(choices);
    }
}
