package comatchingfc.comatchingfc.match.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comatchingfc.comatchingfc.match.dto.req.MatchReq;
import comatchingfc.comatchingfc.match.dto.res.MatchRes;
import comatchingfc.comatchingfc.match.service.MatchService;
import comatchingfc.comatchingfc.utils.response.Response;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/user/match")
@RequiredArgsConstructor
public class MatchController {

	private final MatchService matchService;
	@PostMapping("/request")
	public Response<MatchRes> requestMatch(@RequestBody MatchReq matchReq){
		MatchRes response = matchService.requestMatch(matchReq);

		return Response.ok(response);
	}
}
