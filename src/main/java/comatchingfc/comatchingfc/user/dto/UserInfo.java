package comatchingfc.comatchingfc.user.dto;

import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import comatchingfc.comatchingfc.user.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfo {
    private String username;
    private String socialId;
    private String cheeringPlayer;
    private int age;
    private Gender gender;
    private CheerPropensityEnum cheerPropensity;
}
