package comatchingfc.comatchingfc.user.dto;

import comatchingfc.comatchingfc.player.dto.PlayerRes;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PropensityRes {
    private String cheerPropensity;
    private List<PlayerRes> players;
}
