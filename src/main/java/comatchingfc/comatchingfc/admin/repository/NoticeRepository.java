package comatchingfc.comatchingfc.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import comatchingfc.comatchingfc.admin.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	List<Notice> findAll();

}
