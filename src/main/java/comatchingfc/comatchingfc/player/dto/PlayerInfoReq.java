package comatchingfc.comatchingfc.player.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PlayerInfoReq {

    @NotNull
    @NotEmpty
    private String name;

    private int backNumber;

    @NotNull
    @NotEmpty
    private String position;

    @NotNull
    @NotEmpty
    private String cheerPropensity;
}
