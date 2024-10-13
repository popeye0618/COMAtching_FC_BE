package comatchingfc.comatchingfc.user.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import comatchingfc.comatchingfc.admin.entity.Notice;
import comatchingfc.comatchingfc.admin.repository.NoticeRepository;
import comatchingfc.comatchingfc.exception.BusinessException;
import comatchingfc.comatchingfc.user.dto.res.UserNoticeRes;
import comatchingfc.comatchingfc.user.entity.UserNoticeCheck;
import comatchingfc.comatchingfc.user.entity.Users;
import comatchingfc.comatchingfc.user.repository.UserNoticeCheckRepository;
import comatchingfc.comatchingfc.utils.response.ResponseCode;
import comatchingfc.comatchingfc.utils.security.SecurityUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserNoticeService {

	private final SecurityUtil securityUtil;
	private final NoticeRepository noticeRepository;
	private final UserNoticeCheckRepository userNoticeCheckRepository;

	/**
	 * 관리자가 등록한 Notice 중 사용자가 읽지 않은 Notice만 응답
	 * @return 읽지 않은 Notice 내용, 만료일
	 */

	@Transactional
	public List<UserNoticeRes> inquiryUserNotice(){
		Users user = securityUtil.getCurrentUserEntity();

		List<UserNoticeCheck> readList = user.getUserNoticeCheckList();
		List<Notice> noticeList = noticeRepository.findAll();

		if(readList.size() == noticeList.size()){
			throw new BusinessException(ResponseCode.NO_NOTICE);
		}

		Set<Notice> noticeSet = new HashSet<>(noticeList);
		List<UserNoticeRes> response = new ArrayList<>();

		for (UserNoticeCheck userNoticeCheck : readList) {
			Notice notice = userNoticeCheck.getNotice();

			if (!noticeSet.contains(notice)) {
				userNoticeCheck.updateIsRead(true);
				response.add(new UserNoticeRes(notice.getBody(), notice.getExpireDate()));
			}
		}

		return response;

	}
}
