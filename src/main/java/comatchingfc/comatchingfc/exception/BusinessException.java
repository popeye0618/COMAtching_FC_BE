package comatchingfc.comatchingfc.exception;

import comatchingfc.comatchingfc.utils.response.ResponseCode;
import lombok.Builder;
import lombok.Getter;

public class BusinessException extends RuntimeException{
	@Getter
	private final ResponseCode responseCode;

	@Builder
	public BusinessException(ResponseCode responseCode) {
		super(responseCode.getMessage());
		this.responseCode= responseCode;
	}

}
