package comatchingfc.comatchingfc.admin.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegisterReq {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String accountId;

    @NotNull
    @NotEmpty
    private String password;
}
