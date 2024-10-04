package comatchingfc.comatchingfc.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeatureReq {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String gender;

    private int age;

    @NotNull
    @NotEmpty
    private String socialId;

    @NotNull
    @NotEmpty
    private String cheeringPlayer;
}
