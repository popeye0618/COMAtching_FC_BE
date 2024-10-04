package comatchingfc.comatchingfc.auth.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserLoginReq {

    @NotNull
    @NotEmpty
    private String type;

    @NotNull
    @NotEmpty
    private String ticket;
}
