package comatchingfc.comatchingfc.user.repository;

import comatchingfc.comatchingfc.user.entity.UserFeature;
import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import comatchingfc.comatchingfc.user.enums.Gender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeatureRepository extends JpaRepository<UserFeature, Long> {

	@Query("SELECT COUNT(uf) FROM UserFeature uf " +
		"WHERE uf.userFeatureAiInfo IS NOT NULL " +
		"AND uf.userFeatureAiInfo.isPicked = false " +
		"AND uf.gender = :gender " +
		"AND uf.propensity = :propensity " +
		"AND uf.age > 19 " +
		"AND uf.age BETWEEN :age - 5 AND :age + 5")
	long countMatchableUserByGenderAndPropensityAndAge(
		@Param("gender") String gender,
		@Param("propensity") CheerPropensityEnum propensity,
		@Param("age") int age);


	@Query("SELECT COUNT(uf) FROM UserFeature uf " +
		"WHERE uf.userFeatureAiInfo IS NOT NULL " +
		"AND uf.userFeatureAiInfo.isPicked = false " +
		"AND uf.propensity = :propensity " +
		"AND uf.age > 19 " +
		"AND uf.age BETWEEN :age - 5 AND :age + 5")
	long countMatchableUserAndPropensityAndAge(
		@Param("propensity") CheerPropensityEnum propensity,
		@Param("age") int age);

}
