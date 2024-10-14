package comatchingfc.comatchingfc.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import comatchingfc.comatchingfc.admin.dto.req.NoticeRegisterReq;
import comatchingfc.comatchingfc.admin.service.NoticeService;
import comatchingfc.comatchingfc.utils.response.Response;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;

	@PostMapping("/auth/admin/api/notice/register")
	public Response<Void> registerNotice(@RequestBody NoticeRegisterReq noticeRegisterReq){
		noticeService.registerNotice(noticeRegisterReq);

		return Response.ok();
	}

}
