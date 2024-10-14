package comatchingfc.comatchingfc.user.entity;

import java.util.ArrayList;
import java.util.List;

import comatchingfc.comatchingfc.admin.entity.Notice;
import comatchingfc.comatchingfc.user.entity.Users;
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
@Table( name = "user_notice_check" )
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserNoticeCheck extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_notice_check_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notice_id")
	private Notice notice;

	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn(name = "user_id")
	private Users user;

	private boolean isRead;

	public void updateIsRead(boolean isRead){
		this.isRead = isRead;
	};

	@Builder
	public UserNoticeCheck(Notice notice, Users user, boolean isRead){
		this.notice = notice;
		this.user = user;
		this.isRead = isRead;
	}

}
