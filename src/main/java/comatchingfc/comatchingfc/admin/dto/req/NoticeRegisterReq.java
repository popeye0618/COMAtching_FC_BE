package comatchingfc.comatchingfc.admin.dto.req;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class NoticeRegisterReq {

	private String title;
	private String body;
	private LocalDate expireDate;
}
