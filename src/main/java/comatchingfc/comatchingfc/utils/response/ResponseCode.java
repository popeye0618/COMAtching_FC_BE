package comatchingfc.comatchingfc.utils.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {

	//General response
	SUCCESS(200, "GEN-000", HttpStatus.OK, "Success"),
	BAD_REQUEST(400, "GEN-001", HttpStatus.BAD_REQUEST, "Bad Request"),
	INTERNAL_SERVER_ERROR(500, "GEN-002", HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred");

	private final Integer status;
	private final String code;
	private final HttpStatus httpStatus;
	private final String message;

	ResponseCode(Integer status, String code, HttpStatus httpStatus, String message) {
		this.status = status;
		this.code = code;
		this.httpStatus = httpStatus;
		this.message = message;
	}

}
