package comatchingfc.comatchingfc.match.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import comatchingfc.comatchingfc.match.entity.MatchingHistory;

public interface MatchingHistoryRepository extends JpaRepository<MatchingHistory, Long> {

}
