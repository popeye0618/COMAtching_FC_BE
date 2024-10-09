package comatchingfc.comatchingfc.match.dto.req;

import comatchingfc.comatchingfc.user.enums.Gender;
import lombok.Getter;

@Getter
public class MatchTestReq {
	private String uuid;
	private Gender genderOption;
}
