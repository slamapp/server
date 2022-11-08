package org.slams.server.favorite.repository;

import org.slams.server.court.entity.Court;
import org.slams.server.favorite.entity.Favorite;
import org.slams.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

	List<Favorite> findAllByUserOrderByCreatedAtDesc(User user);

	Optional<Favorite> findByCourtAndUser(Court court, User user);

}
