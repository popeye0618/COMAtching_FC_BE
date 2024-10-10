package comatchingfc.comatchingfc.match.dto.res;

import comatchingfc.comatchingfc.match.dto.req.MatchReq;
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

	public static MatchRes testDto(){
		return MatchRes.builder()
			.age(25)
			.gender(Gender.MALE)
			.username("ex_user")
			.propensity(CheerPropensityEnum.열정형)
			.cheeringPlayer("Ronaldo")
			.socialId("@instagram")
			.lackOfResource(true)
		.build();
	}
}
