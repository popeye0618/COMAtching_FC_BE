package comatchingfc.comatchingfc.survey.choice.entity;

import comatchingfc.comatchingfc.survey.question.entity.Question;
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
public class Choice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "choice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyResponse> surveyResponses = new ArrayList<>();

    private String choiceText;

    private String cheerPropensity;

    private int score;

    @Builder
    public Choice(String choiceText, String cheerPropensity, int score) {
        this.choiceText = choiceText;
        this.cheerPropensity = cheerPropensity;
        this.score = score;
    }

    public void setQuestion(Question question) {
        if (this.question != null) {
            this.question.getChoices().remove(this);
        }
        this.question = question;
        if (question != null && !question.getChoices().contains(this)) {
            question.getChoices().add(this);
        }
    }

    public void addSurveyResponse(SurveyResponse surveyResponse) {
        surveyResponses.add(surveyResponse);
        if (surveyResponse.getChoice() != this) {
            surveyResponse.setChoice(this);
        }
    }

    public void removeSurveyResponse(SurveyResponse surveyResponse) {
        surveyResponses.remove(surveyResponse);
        if (surveyResponse.getChoice() == this) {
            surveyResponse.setChoice(null);
        }
    }
}
