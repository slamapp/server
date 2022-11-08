package org.slams.server.favorite.entity;

import lombok.*;
import org.slams.server.common.BaseEntity;
import org.slams.server.court.entity.Court;
import org.slams.server.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by yunyun on 2021/12/03.
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "favorite", uniqueConstraints = {@UniqueConstraint(columnNames = {"court_id", "user_id"})})
public class Favorite extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "court_id", nullable = false)
	private Court court;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Favorite(Court court, User user) {
		this.court = court;
		this.user = user;
	}

	@Builder
	public Favorite(LocalDateTime createdAt, LocalDateTime updatedAt, Long id, Court court, User user) {
		super(createdAt, updatedAt);
		this.id = id;
		this.court = court;
		this.user = user;
	}

}
