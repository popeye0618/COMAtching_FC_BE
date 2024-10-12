package comatchingfc.comatchingfc.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import comatchingfc.comatchingfc.admin.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
