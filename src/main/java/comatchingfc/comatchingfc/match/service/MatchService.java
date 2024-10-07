package comatchingfc.comatchingfc.match.service;

import org.springframework.stereotype.Service;

import comatchingfc.comatchingfc.match.dto.req.AuthReq;
import comatchingfc.comatchingfc.match.dto.req.MatchReq;
import comatchingfc.comatchingfc.utils.rabbitMQ.AuthRabbitMQUtil;
import comatchingfc.comatchingfc.utils.rabbitMQ.MatchingRabbitMQUtil;
import comatchingfc.comatchingfc.utils.rabbitMQ.Message.req.MatchReqMsg;
import comatchingfc.comatchingfc.utils.rabbitMQ.Message.res.MatchResMsg;
import comatchingfc.comatchingfc.utils.response.Response;
import comatchingfc.comatchingfc.utils.response.ResponseCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchService {

	private final AuthRabbitMQUtil authRabbitMQUtil;
	private final MatchingRabbitMQUtil matchingRabbitMQUtil;

	public Response requestAuth(AuthReq req){
		boolean isSuccess = authRabbitMQUtil.checkReserveNumber(req.getReserveCode());

		if(isSuccess){
			return Response.ok();
		}

		return Response.errorResponse(ResponseCode.BAD_REQUEST);
	}

	public Response requestMatch(MatchReq req){

		MatchReqMsg request = MatchReqMsg.majeTest(req.getUuid());
		MatchResMsg response = matchingRabbitMQUtil.requestMatch(request);

		return Response.ok();
	}
}
