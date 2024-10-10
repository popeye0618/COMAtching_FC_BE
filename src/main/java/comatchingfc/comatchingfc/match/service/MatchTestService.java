package comatchingfc.comatchingfc.match.service;

import org.springframework.stereotype.Service;

import comatchingfc.comatchingfc.match.dto.req.AuthReq;
import comatchingfc.comatchingfc.match.dto.req.MatchTestReq;
import comatchingfc.comatchingfc.utils.rabbitMQ.AuthRabbitMQUtil;
import comatchingfc.comatchingfc.utils.rabbitMQ.MatchingRabbitMQUtil;
import comatchingfc.comatchingfc.utils.rabbitMQ.Message.req.MatchReqMsg;
import comatchingfc.comatchingfc.utils.rabbitMQ.Message.req.UserCrudReqMsg;
import comatchingfc.comatchingfc.utils.rabbitMQ.Message.res.MatchResMsg;
import comatchingfc.comatchingfc.utils.rabbitMQ.UserRabbitMQUtil;
import comatchingfc.comatchingfc.utils.response.Response;
import comatchingfc.comatchingfc.utils.response.ResponseCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchTestService {

	private final AuthRabbitMQUtil authRabbitMQUtil;
	private final MatchingRabbitMQUtil matchingRabbitMQUtil;
	private final UserRabbitMQUtil userRabbitMQUtil;

	public Response requestAuth(AuthReq req){
		boolean isSuccess = authRabbitMQUtil.checkReserveNumber(req.getReserveCode()).isAuthSuccess();

		if(isSuccess){
			return Response.ok();
		}

		return Response.errorResponse(ResponseCode.BAD_REQUEST);
	}

	public Response requestMatch(MatchTestReq req){

		MatchReqMsg request = MatchReqMsg.majeTest(req.getUuid());
		MatchResMsg response = matchingRabbitMQUtil.requestMatch(request);

		return Response.ok();
	}

	public Response requestUserCrud(UserCrudReqMsg req){

 		userRabbitMQUtil.requestUserToCsv(req, req.getType());

		return Response.ok();
	}
}
