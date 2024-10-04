package comatchingfc.comatchingfc.survey.question.service;

import comatchingfc.comatchingfc.survey.choice.dto.CheerPropensityProjection;
import comatchingfc.comatchingfc.survey.choice.dto.ChoiceProjection;
import comatchingfc.comatchingfc.survey.choice.dto.ChoiceRes;
import comatchingfc.comatchingfc.survey.choice.entity.ChoiceCheerPropensity;
import comatchingfc.comatchingfc.survey.choice.service.ChoiceService;
import comatchingfc.comatchingfc.survey.dto.SurveyReq;
import comatchingfc.comatchingfc.survey.question.dto.QuestionProjection;
import comatchingfc.comatchingfc.survey.question.dto.QuestionRes;
import comatchingfc.comatchingfc.survey.question.entity.Question;
import comatchingfc.comatchingfc.survey.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ChoiceService choiceService;

    @Transactional
    @CacheEvict(value = "questionsWithChoicesAndScores", key = "'allQuestions'", allEntries = true)
    public void saveQuestion(SurveyReq surveyReq) {
        Question question = Question.builder()
                .content(surveyReq.getQuestionContent())
                .build();

        questionRepository.save(question);

        choiceService.saveChoiceWithScore(question, surveyReq.getChoiceTexts(), surveyReq.getChoiceScores());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "questionsWithChoicesAndScores", key = "'allQuestions'")
    public List<QuestionRes> getAllQuestionsWithChoicesAndScores() {
        // 모든 질문을 프로젝션 형태로 데이터베이스에서 조회
        List<Question> questions = questionRepository.findAll();

        return questions.stream()
                .map(q -> QuestionRes.builder()
                        .content(q.getContent())
                        .choices(q.getChoices().stream()
                                .map(c -> ChoiceRes.builder()
                                        .choiceText(c.getChoiceText())
                                        .cheerPropensities(c.getCheerPropensities().stream()
                                                .collect(Collectors.toMap(
                                                        ChoiceCheerPropensity::getCheerPropensityEnum,
                                                        ChoiceCheerPropensity::getScore
                                                ))
                                        )
                                        .build()
                                ).collect(Collectors.toList())
                        )
                        .build()
                ).collect(Collectors.toList());
    }
}
