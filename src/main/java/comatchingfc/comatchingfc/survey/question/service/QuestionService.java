package comatchingfc.comatchingfc.survey.question.service;

import comatchingfc.comatchingfc.survey.choice.dto.CheerPropensityProjection;
import comatchingfc.comatchingfc.survey.choice.dto.ChoiceProjection;
import comatchingfc.comatchingfc.survey.choice.dto.ChoiceRes;
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
        List<QuestionProjection> projections = questionRepository.findAllProjectedBy();

        // 질문 내용과 해당 선택지 리스트를 저장할 LinkedHashMap 생성
        // LinkedHashMap을 사용하여 삽입 순서를 유지
        Map<String, List<ChoiceRes>> questionMap = new LinkedHashMap<>();

        // 조회한 각 질문에 대해 반복 처리
        for (QuestionProjection q : projections) {
            String content = q.getContent(); // 질문의 내용 가져오기
            List<ChoiceProjection> choices = q.getChoices(); // 해당 질문의 선택지 목록 가져오기

            // 선택지 리스트를 ChoiceRes 객체 리스트로 변환
            List<ChoiceRes> choiceResList = choices.stream()
                    .map(choice -> {
                        // 각 선택지의 CheerPropensity를 Map 형태로 변환
                        Map<String, Integer> cheerMap = choice.getCheerPropensities().stream()
                                .collect(Collectors.toMap(
                                        CheerPropensityProjection::getCheerPropensityEnum,
                                        CheerPropensityProjection::getScore
                                ));
                        return ChoiceRes.builder()
                                .choiceText(choice.getChoiceText())
                                .cheerPropensities(cheerMap)
                                .build();
                    })
                    .collect(Collectors.toList());

            // 질문 내용과 변환된 선택지 리스트를 Map에 추가
            questionMap.put(content, choiceResList);
        }

        // Map에 저장된 질문과 선택지 정보를 QuestionRes 객체 리스트로 변환
        return questionMap.entrySet().stream()
                .map(entry -> QuestionRes.builder()
                        .content(entry.getKey())
                        .choices(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}
