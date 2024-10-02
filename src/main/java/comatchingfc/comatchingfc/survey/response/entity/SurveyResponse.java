package comatchingfc.comatchingfc.survey.response.entity;

import comatchingfc.comatchingfc.survey.choice.entity.Choice;
import comatchingfc.comatchingfc.survey.question.entity.Question;
import comatchingfc.comatchingfc.user.entity.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "question_id"})
)
public class SurveyResponse {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "choice_id", nullable = false)
    private Choice choice;

    @Builder
    public SurveyResponse(Users user, Question question, Choice choice) {
        if (!choice.getQuestion().equals(question)) {
            throw new IllegalArgumentException("선택지는 해당 질문에 속하지 않습니다.");
        }
        this.user = user;
        this.question = question;
        this.choice = choice;

        user.addSurveyResponse(this);
        question.addSurveyResponse(this);
        choice.addSurveyResponse(this);
    }

    public void setUser(Users user) {
        if (this.user != null) {
            this.user.getSurveyResponses().remove(this);
        }
        this.user = user;
        if (user != null && !user.getSurveyResponses().contains(this)) {
            user.getSurveyResponses().add(this);
        }
    }

    public void setQuestion(Question question) {
        if (this.question != null) {
            this.question.getSurveyResponses().remove(this);
        }
        this.question = question;
        if (question != null && !question.getSurveyResponses().contains(this)) {
            question.getSurveyResponses().add(this);
        }
    }

    public void setChoice(Choice choice) {
        if (this.choice != null) {
            this.choice.getSurveyResponses().remove(this);
        }
        this.choice = choice;
        if (choice != null && !choice.getSurveyResponses().contains(this)) {
            choice.getSurveyResponses().add(this);
        }
    }
}
