package comatchingfc.comatchingfc.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import comatchingfc.comatchingfc.user.entity.UserNoticeCheck;

public interface UserNoticeCheckRepository extends JpaRepository<UserNoticeCheck, Long> {

	List<UserNoticeCheck> findAll();
}
