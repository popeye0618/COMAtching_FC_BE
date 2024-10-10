package comatchingfc.comatchingfc.user.repository;

import java.util.Optional;

import comatchingfc.comatchingfc.user.entity.UserAiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAiInfoRepository extends JpaRepository<UserAiInfo, Long> {

	Optional<UserAiInfo> findByUuid(byte[] uuid);
}
