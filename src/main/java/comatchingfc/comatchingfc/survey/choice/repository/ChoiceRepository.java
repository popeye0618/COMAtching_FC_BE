package comatchingfc.comatchingfc.survey.choice.repository;

import comatchingfc.comatchingfc.survey.choice.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
