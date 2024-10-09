package comatchingfc.comatchingfc.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoRes {
    private UserInfo myInfo;
    private UserInfo enemyInfo;
}
