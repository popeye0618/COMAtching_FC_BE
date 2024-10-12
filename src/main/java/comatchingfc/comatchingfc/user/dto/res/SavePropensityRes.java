package comatchingfc.comatchingfc.user.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import comatchingfc.comatchingfc.auth.jwt.dto.TokenRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SavePropensityRes {

    private PropensityRes propensityRes;

    @JsonIgnore
    private TokenRes tokenRes;
}
