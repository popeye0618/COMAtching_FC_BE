package comatchingfc.comatchingfc.survey.question.repository;

import comatchingfc.comatchingfc.survey.question.dto.QuestionProjection;
import comatchingfc.comatchingfc.survey.question.entity.Question;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @EntityGraph(attributePaths = {"choices", "choices.cheerPropensities"})
    List<Question> findAll();
}
