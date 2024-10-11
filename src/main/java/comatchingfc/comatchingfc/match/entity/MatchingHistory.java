package comatchingfc.comatchingfc.match.entity;

import comatchingfc.comatchingfc.user.entity.UserFeature;
import comatchingfc.comatchingfc.user.entity.Users;
import comatchingfc.comatchingfc.utils.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matching_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingHistory extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "matching_history_id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "applier_id")
	private Users applier;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "enemy_id")
	private Users enemy;

	@Builder
	public MatchingHistory(Users applier, Users enemy, UserFeature userFeature){
		this.applier = applier;
		this.enemy = enemy;

		applier.addMatchingHistory(this);
		enemy.addMatchingHistory(this);
	}
}
