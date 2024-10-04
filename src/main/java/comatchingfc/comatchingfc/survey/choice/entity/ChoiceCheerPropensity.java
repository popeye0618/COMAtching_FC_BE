package comatchingfc.comatchingfc.survey.choice.entity;

import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChoiceCheerPropensity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_cheer_propensity_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice_id")
    private Choice choice;

    @Enumerated(EnumType.STRING)
    private CheerPropensityEnum cheerPropensityEnum;

    private int score;

    @Builder
    public ChoiceCheerPropensity(CheerPropensityEnum cheerPropensityEnum, int score) {
        this.cheerPropensityEnum = cheerPropensityEnum;
        this.score = score;
    }

    public void setChoice(Choice choice) {
        if (this.choice != null) {
            this.choice.getCheerPropensities().remove(this);
        }
        this.choice = choice;
        if (choice != null && !choice.getCheerPropensities().contains(this)) {
            choice.getCheerPropensities().add(this);
        }
    }
}
