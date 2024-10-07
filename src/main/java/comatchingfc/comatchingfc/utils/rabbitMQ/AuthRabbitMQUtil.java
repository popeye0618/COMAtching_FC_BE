package comatchingfc.comatchingfc.utils.rabbitMQ;

import java.util.UUID;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import comatchingfc.comatchingfc.utils.rabbitMQ.Message.req.ReserveAuthReq;
import comatchingfc.comatchingfc.utils.rabbitMQ.Message.res.ReserveAuthRes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthRabbitMQUtil {

	private final RabbitTemplate rabbitTemplate;

	@Value("${rabbitmq.routing-keys.reserve-auth}")
	private String matchRequestQueue;

	public AuthRabbitMQUtil(RabbitTemplate rabbitTemplate){
		this.rabbitTemplate = rabbitTemplate;
	}

	/**
	 * 예매 번호 인증 요청
	 * @param reserveNumber : 예매번호
	 * @return : boolean - 인증 성공 여부
	 */
	public boolean checkReserveNumber(String reserveNumber){
		String requestId = UUID.randomUUID().toString();

		ReserveAuthReq requestMsg = new ReserveAuthReq(reserveNumber);
		CorrelationData correlationData = new CorrelationData(requestId);
		ParameterizedTypeReference<ReserveAuthRes> responseType = new ParameterizedTypeReference<ReserveAuthRes>(){};


		ReserveAuthRes responseMsg =  rabbitTemplate.convertSendAndReceiveAsType(
			matchRequestQueue,
			requestMsg,
			(MessagePostProcessor) null,
			correlationData,
			responseType);

		return responseMsg.isAuthSuccess();
	}
}
