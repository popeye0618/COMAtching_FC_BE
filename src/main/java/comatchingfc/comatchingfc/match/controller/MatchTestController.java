package comatchingfc.comatchingfc.match.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import comatchingfc.comatchingfc.match.dto.req.AuthReq;
import comatchingfc.comatchingfc.match.dto.req.MatchTestReq;
import comatchingfc.comatchingfc.match.service.MatchTestService;
import comatchingfc.comatchingfc.utils.rabbitMQ.Message.req.UserCrudReqMsg;
import comatchingfc.comatchingfc.utils.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MatchTestController {

	private final MatchTestService matchService;

	@PostMapping("/test/match/auth/reserve")
	public Response requestAuth(@RequestBody AuthReq req) {

		return matchService.requestAuth(req);
	}

	@PostMapping("/test/match/request")
	public Response requestMatch(@RequestBody MatchTestReq req) {
		log.info("here");
		return matchService.requestMatch(req);
	}

	@PostMapping("/test/match/user/crud")
	public Response requestMatch(@RequestBody UserCrudReqMsg req) {
		log.info("here");
		return matchService.requestUserCrud(req);
	}
}
