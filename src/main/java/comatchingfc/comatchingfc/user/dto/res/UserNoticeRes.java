package comatchingfc.comatchingfc.user.dto.res;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserNoticeRes {

	private String body;
	private LocalDate expireDate;
}
