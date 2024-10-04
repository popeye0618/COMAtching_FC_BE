package comatchingfc.comatchingfc.survey.choice.entity;

import comatchingfc.comatchingfc.survey.question.entity.Question;
import comatchingfc.comatchingfc.utils.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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
    private Set<ChoiceCheerPropensity> cheerPropensities = new HashSet<>();

    private String choiceText;

    @Builder
    public Choice(String choiceText, Question question) {
        this.choiceText = choiceText;
        setQuestion(question);
    }

    public void addCheerPropensity(ChoiceCheerPropensity cheerPropensity) {
        this.cheerPropensities.add(cheerPropensity);
        cheerPropensity.setChoice(this);
    }

    public void removeCheerPropensity(ChoiceCheerPropensity cheerPropensity) {
        this.cheerPropensities.remove(cheerPropensity);
        cheerPropensity.setChoice(null);
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
}
