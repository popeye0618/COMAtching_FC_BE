package comatchingfc.comatchingfc.player.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRes {
    private String playerName;
    private int backNumber;
    private String position;
}
