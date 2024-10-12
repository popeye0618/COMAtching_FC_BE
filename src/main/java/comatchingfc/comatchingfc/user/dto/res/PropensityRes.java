package comatchingfc.comatchingfc.user.dto.res;

import comatchingfc.comatchingfc.player.dto.PlayerRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PropensityRes {

    private String cheerPropensity;

    private List<PlayerRes> players;
}
