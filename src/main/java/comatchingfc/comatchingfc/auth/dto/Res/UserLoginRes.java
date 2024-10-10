package comatchingfc.comatchingfc.auth.dto.Res;

import com.fasterxml.jackson.annotation.JsonIgnore;

import comatchingfc.comatchingfc.auth.jwt.dto.TokenRes;
import comatchingfc.comatchingfc.user.enums.TeamSide;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRes {
	private TeamSide teamSide;
	private String role;

	@JsonIgnore
	private TokenRes tokenRes;
}
