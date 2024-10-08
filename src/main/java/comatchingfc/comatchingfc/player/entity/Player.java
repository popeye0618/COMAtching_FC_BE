package comatchingfc.comatchingfc.player.entity;

import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Player {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;

    private String name;

    private int backNumber;

    private String position;

    private CheerPropensityEnum cheerPropensityEnum;

    @Builder
    public Player(String name, int backNumber, String position, CheerPropensityEnum cheerPropensityEnum) {
        this.name = name;
        this.backNumber = backNumber;
        this.position = position;
        this.cheerPropensityEnum = cheerPropensityEnum;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateBackNumber(int backNumber) {
        this.backNumber = backNumber;
    }

    public void updateCheerPropensity(CheerPropensityEnum cheerPropensityEnum) {
        this.cheerPropensityEnum = cheerPropensityEnum;
    }
}
