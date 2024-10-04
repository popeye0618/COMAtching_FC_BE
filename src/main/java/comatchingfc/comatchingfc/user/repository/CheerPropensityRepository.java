package comatchingfc.comatchingfc.user.repository;

import comatchingfc.comatchingfc.user.entity.CheerPropensity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheerPropensityRepository extends JpaRepository<CheerPropensity, Long> {
}
