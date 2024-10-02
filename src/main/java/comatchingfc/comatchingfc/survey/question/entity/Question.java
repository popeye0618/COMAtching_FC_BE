package comatchingfc.comatchingfc.survey.question.entity;

import comatchingfc.comatchingfc.survey.choice.entity.Choice;
import comatchingfc.comatchingfc.survey.response.entity.SurveyResponse;
import comatchingfc.comatchingfc.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Choice> choices = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyResponse> surveyResponses = new ArrayList<>();

    private String content;

    @Builder
    public Question(String content) {
        this.content = content;
    }

    public void addChoice(Choice choice) {
        choices.add(choice);
        choice.setQuestion(this);
    }

    public void removeChoice(Choice choice) {
        choices.remove(choice);
        choice.setQuestion(null);
    }

    public void addSurveyResponse(SurveyResponse surveyResponse) {
        surveyResponses.add(surveyResponse);
        if (surveyResponse.getQuestion() != this) {
            surveyResponse.setQuestion(this);
        }
    }

    public void removeSurveyResponse(SurveyResponse surveyResponse) {
        surveyResponses.remove(surveyResponse);
        if (surveyResponse.getQuestion() == this) {
            surveyResponse.setQuestion(null);
        }
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
