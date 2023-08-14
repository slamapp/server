package org.slams.server.reservation.repository;

import org.slams.server.court.entity.Court;
import org.slams.server.court.entity.NewCourt;
import org.slams.server.follow.entity.Follow;
import org.slams.server.reservation.entity.Reservation;
import org.slams.server.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	boolean existsByUserAndEndTimeGreaterThanAndStartTimeLessThan(User user, LocalDateTime startTime, LocalDateTime endTime);


	@Query("SELECT r FROM Reservation r JOIN FETCH r.court WHERE r.user.id=:userId AND r.startTime>:localDateTime ORDER BY r.startTime ASC, r.endTime ASC")
	List<Reservation> findByUserFromStartTime(
		@Param("userId") Long userId,
		@Param("localDateTime") LocalDateTime localDateTime
	);

    @Query("SELECT r.user.id FROM Reservation r WHERE r.court.id=:courtId")
    List<Long> findBeakerIdByCourtId(
            @Param("courtId") Long courtId
    );

    @Query("SELECT COUNT(DISTINCT r.user.id) FROM Reservation r WHERE r.court.id=:courtId AND ((r.startTime BETWEEN :startTime AND :endTime) OR (r.endTime BETWEEN :startTime AND :endTime))")
    Long countUserByCourtAndTime(
        @Param("courtId") Long courtId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );

    @Query("SELECT distinct r.user FROM Reservation r WHERE r.court.id = :courtId AND ((:startTime BETWEEN r.startTime AND r.endTime) OR (:endTime BETWEEN r.startTime AND r.endTime))")
    List<User> findUsersByCourtIdAndTime(
        @Param("courtId") Long courtId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );

    // 유저의 지난 예약 목록(무한 스크롤 - 최초)
    @Query("SELECT r FROM Reservation r JOIN FETCH r.court WHERE r.user.id = :userId AND r.endTime < :localDateTime ORDER BY r.id desc")
    List<Reservation> findByUserFromEndTimeOrderByIdDesc(
        @Param("userId") Long userId, @Param("localDateTime") LocalDateTime localDateTime, Pageable pageable
    );
    // 유저의 지난 예약 목록(무한 스크롤)
    @Query("SELECT r FROM Reservation r JOIN FETCH r.court WHERE r.id < :lastId order by r.id desc")
    List<Reservation> findByIdLessThanOrderByIdDesc(@Param("lastId") Long lastId, Pageable pageable);

	// 유저의 지난 예약 목록 최신순(무한 스크롤 - 최초)
	@Query("SELECT r FROM Reservation r JOIN FETCH r.court WHERE r.user.id = :userId AND r.endTime < :localDateTime ORDER BY r.startTime desc")
	List<Reservation> findByUserFromEndTimeOrderByStartTimeDesc(
		@Param("userId") Long userId, @Param("localDateTime") LocalDateTime localDateTime, Pageable pageable
	);
	// 유저의 지난 예약 목록 최신순(무한 스크롤)
	@Query("SELECT r FROM Reservation r JOIN FETCH r.court WHERE r.endTime < :lastStartTime ORDER BY r.startTime desc")
	List<Reservation> findByStartTimeLessThanOrderByStartTimeDesc(@Param("lastStartTime") LocalDateTime lastStartTime, Pageable pageable);

    Optional<Reservation> findByCourtAndUser(Court court, User user);

	@Query("SELECT r FROM Reservation r JOIN FETCH r.user WHERE r.court.id = :courtId AND r.startTime < :endTime AND r.endTime > :startTime ORDER BY r.startTime")
	List<Reservation> findByCourtIdAndDateBetweenOrderByStartTime(
		@Param("courtId") Long courtId,
		@Param("startTime") LocalDateTime startTime,
		@Param("endTime") LocalDateTime endTime
	);

}
