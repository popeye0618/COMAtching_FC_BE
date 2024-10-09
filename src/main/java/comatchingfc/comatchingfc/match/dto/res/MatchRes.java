package comatchingfc.comatchingfc.match.dto.res;

import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import comatchingfc.comatchingfc.user.enums.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchRes {
	private int age;
	private Gender gender;
	private String username;
	private CheerPropensityEnum propensity;
	private String cheeringPlayer;
	private String socialId;
	private boolean lackOfResource;
}
