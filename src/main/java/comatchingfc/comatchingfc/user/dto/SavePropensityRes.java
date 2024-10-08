package comatchingfc.comatchingfc.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import comatchingfc.comatchingfc.auth.jwt.dto.TokenRes;
import comatchingfc.comatchingfc.player.dto.PlayerRes;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SavePropensityRes {

    private PropensityRes propensityRes;

    @JsonIgnore
    private TokenRes tokenRes;
}
