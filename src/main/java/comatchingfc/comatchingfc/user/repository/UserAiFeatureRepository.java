package comatchingfc.comatchingfc.user.repository;

import comatchingfc.comatchingfc.user.entity.UserAiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAiFeatureRepository extends JpaRepository<UserAiInfo, Long> {
}
