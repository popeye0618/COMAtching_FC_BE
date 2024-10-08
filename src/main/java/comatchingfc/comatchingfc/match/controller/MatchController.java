package comatchingfc.comatchingfc.match.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import comatchingfc.comatchingfc.match.dto.req.AuthReq;
import comatchingfc.comatchingfc.match.dto.req.MatchReq;
import comatchingfc.comatchingfc.match.service.MatchService;
import comatchingfc.comatchingfc.utils.rabbitMQ.Message.req.UserCrudReqMsg;
import comatchingfc.comatchingfc.utils.response.Response;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MatchController {

	private final MatchService matchService;

	@PostMapping("/test/match/auth/reserve")
	public Response requestAuth(@RequestBody AuthReq req){

		return matchService.requestAuth(req);
	}

	@PostMapping("/test/match/request")
	public Response requestMatch(@RequestBody MatchReq req){

		return matchService.requestMatch(req);
	}

	@PostMapping("/test/match/request")
	public Response requestMatch(@RequestBody UserCrudReqMsg req){

		return matchService.requestUserCrud(req);
	}


}
