package comatchingfc.comatchingfc.match.dto.req;

import comatchingfc.comatchingfc.user.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchReq {

	private Gender genderOption;
}
