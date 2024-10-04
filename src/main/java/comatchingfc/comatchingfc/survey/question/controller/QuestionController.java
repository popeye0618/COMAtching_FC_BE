package comatchingfc.comatchingfc.survey.question.controller;

import comatchingfc.comatchingfc.survey.dto.SurveyReq;
import comatchingfc.comatchingfc.survey.question.service.QuestionService;
import comatchingfc.comatchingfc.utils.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/auth/admin/question")
    public Response<Void> addQuestion(@RequestBody @Valid SurveyReq surveyReq) {
        questionService.saveQuestion(surveyReq);
        return Response.ok();
    }

    @GetMapping("/survey/question")
    public Response<?> getAllQuestions() {
        return Response.ok(questionService.getAllQuestionsWithChoicesAndScores());
    }
}
