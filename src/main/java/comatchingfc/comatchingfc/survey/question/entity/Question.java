package comatchingfc.comatchingfc.survey.question.entity;

import comatchingfc.comatchingfc.survey.choice.entity.Choice;
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
public class Question extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Choice> choices = new HashSet<>();

    private String content;

    @Builder
    public Question(String content) {
        this.content = content;
    }

    public void addChoice(Choice choice) {
        this.choices.add(choice);
        choice.setQuestion(this);
    }

    public void removeChoice(Choice choice) {
        this.choices.remove(choice);
        choice.setQuestion(null);
    }


    public void updateContent(String content) {
        this.content = content;
    }
}
