package comatchingfc.comatchingfc.match.dto.req;

import comatchingfc.comatchingfc.user.enums.Gender;
import lombok.Getter;

@Getter
public class MatchReq {
	private String uuid;
	private Gender genderOption;
}
