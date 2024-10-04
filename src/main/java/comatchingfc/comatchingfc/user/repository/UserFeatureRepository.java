package comatchingfc.comatchingfc.user.repository;

import comatchingfc.comatchingfc.user.entity.UserFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeatureRepository extends JpaRepository<UserFeature, Long> {
}
