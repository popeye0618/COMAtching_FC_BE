package comatchingfc.comatchingfc.survey.response.repository;

import comatchingfc.comatchingfc.survey.response.entity.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {
}
