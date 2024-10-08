package comatchingfc.comatchingfc.player.repository;

import comatchingfc.comatchingfc.player.entity.Player;
import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findPlayersByCheerPropensityEnum(CheerPropensityEnum cheerPropensityEnum);
}
