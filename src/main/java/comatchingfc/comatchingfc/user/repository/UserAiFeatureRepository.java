package comatchingfc.comatchingfc.user.repository;

import comatchingfc.comatchingfc.user.entity.UserAiFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAiFeatureRepository extends JpaRepository<UserAiFeature, Long> {
}
