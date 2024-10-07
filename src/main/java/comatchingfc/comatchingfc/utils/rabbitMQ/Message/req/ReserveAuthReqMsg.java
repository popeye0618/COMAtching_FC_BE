package comatchingfc.comatchingfc.utils.rabbitMQ.Message.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveAuthReqMsg {
	private String reserveNumber;
}
