package comatchingfc.comatchingfc.admin.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import comatchingfc.comatchingfc.user.entity.UserNoticeCheck;
import comatchingfc.comatchingfc.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notice_id")
	private Long id;

	private String title;

	private String body;

	private LocalDate expireDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id")
	private Admin admin;

	@OneToMany(mappedBy = "notice")
	private List<UserNoticeCheck> userNoticeCheckList = new ArrayList<>();

	@Builder
	public Notice(String body, LocalDate expireDate, String title ,Admin admin){
		this.body = body;
		this.expireDate = expireDate;
		this.admin =  admin;
	}
}
