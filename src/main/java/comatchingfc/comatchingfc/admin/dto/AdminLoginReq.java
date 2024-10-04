package comatchingfc.comatchingfc.admin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminLoginReq {

    @NotNull
    @NotEmpty
    private String accountId;

    @NotNull
    @NotEmpty
    private String password;
}
