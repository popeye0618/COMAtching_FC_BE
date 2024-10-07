package comatchingfc.comatchingfc.utils.rabbitMQ.Message.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReserveAuthRes {
	private String stateCode;
	private String teamSide;
	private boolean authSuccess;
}
