package org.slams.server.reservation.service;

import lombok.RequiredArgsConstructor;
import org.slams.server.common.api.CursorPageRequest;
import org.slams.server.common.api.CursorPageResponse;
import org.slams.server.common.api.ListResponse;
import org.slams.server.court.entity.Court;
import org.slams.server.court.exception.CourtNotFoundException;
import org.slams.server.court.repository.CourtRepository;
import org.slams.server.follow.entity.Follow;
import org.slams.server.follow.repository.FollowRepository;
import org.slams.server.reservation.dto.request.ReservationInsertRequest;
import org.slams.server.reservation.dto.request.ReservationUpdateRequest;
import org.slams.server.reservation.dto.response.*;
import org.slams.server.reservation.entity.Reservation;
import org.slams.server.reservation.exception.ReservationAlreadyExistException;
import org.slams.server.reservation.exception.ReservationNotFoundException;
import org.slams.server.reservation.repository.ReservationRepository;
import org.slams.server.user.entity.User;
import org.slams.server.user.exception.UserNotAuthorizedException;
import org.slams.server.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CourtRepository courtRepository;
    private final FollowRepository followRepository;

	@Transactional
	public ReservationInsertResponse insert(ReservationInsertRequest request, Long userId) {
		User user = userRepository.getById(userId);

		Court court = courtRepository.findById(request.getCourtId())
			.orElseThrow(() -> new CourtNotFoundException(
				MessageFormat.format("등록된 농구장을 찾을 수 없습니다. id : {0}", request.getCourtId())));

		// todo. 유저가 동일한 시간에 기존 예약이 존재한다면, 예약을 새롭게 추가할 수 없도록 제한한다
        if (reservationRepository.existsByUserAndEndTimeGreaterThanAndStartTimeLessThan(user, request.getStartTime(), request.getEndTime())) {
            throw new ReservationAlreadyExistException("이미 저장된 예약이 있습니다.");
        }

		Reservation reservation = request.toEntity(request, court, user);

		return ReservationInsertResponse.of(reservationRepository.save(reservation));
	}

    @Transactional
    public ReservationUpdateResponse update(ReservationUpdateRequest request, Long reservationId, Long userId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationNotFoundException(
                MessageFormat.format("저장된 예약을 찾을 수 없습니다. id : {0}", reservationId)
            ));

        // 해당 유저만 수정 가능
        if (!userId.equals(reservation.getUser().getId())) {
            throw new UserNotAuthorizedException();
        }

        reservation.update(request.getStartTime(), request.getEndTime(), request.getHasBall());
        reservationRepository.flush(); // updatedAt 반영

        return ReservationUpdateResponse.of(reservation);
    }

    @Transactional
    public void delete(Long reservationId, Long userId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationNotFoundException(
                MessageFormat.format("저장된 예약을 찾을 수 없습니다. id : {0}", reservationId)
            ));

        if (!userId.equals(reservation.getUser().getId())) {
            throw new UserNotAuthorizedException();
        }

        reservationRepository.delete(reservation);
    }

    public ListResponse<ReservationUpcomingResponse> findUpcoming(Long userId) {
        List<Reservation> reservationList = reservationRepository.findByUserFromStartTime(userId, LocalDateTime.now());

        ListResponse<ReservationUpcomingResponse> reservationUpcomingResponseList = new ListResponse<>();

        for (Reservation reservation : reservationList) {
            Long reservationCount = reservationRepository.countUserByCourtAndTime(reservation.getCourt().getId(), reservation.getStartTime(), reservation.getEndTime());

            ReservationUpcomingResponse reservationUpcomingResponse = ReservationUpcomingResponse.of(reservation, reservationCount);
            reservationUpcomingResponseList.addContents(reservationUpcomingResponse);
        }

        return reservationUpcomingResponseList;
    }

    public CursorPageResponse<List<ReservationExpiredResponse>> findExpired(Long userId, CursorPageRequest request) {
        PageRequest pageable = PageRequest.of(0, request.getSize());
        List<Reservation> reservations = request.getIsFirst() ?
            reservationRepository.findByUserFromEndTimeOrderByIdDesc(userId, LocalDateTime.now(), pageable) :
            reservationRepository.findByIdLessThanOrderByIdDesc(request.getLastIdParedForLong(), pageable);

        List<ReservationExpiredResponse> reservationExpiredResponseList = new ArrayList<>();
        for (Reservation reservation : reservations) {
            Long reservationCount = reservationRepository.countUserByCourtAndTime(reservation.getCourt().getId(), reservation.getStartTime(), reservation.getEndTime());
            reservationExpiredResponseList.add(ReservationExpiredResponse.of(reservation, reservationCount));
        }

        String lastId = reservations.size() < request.getSize() ? null : reservations.get(reservations.size() - 1).getId().toString();

        return new CursorPageResponse<>(reservationExpiredResponseList, lastId);
    }

    public ListResponse<ReservationDetailResponse> getDetail(Long userId, Long courtId, LocalDateTime startTime, LocalDateTime endTime) {
        User user = userRepository.getById(userId);

//        LocalDateTime sTime = LocalDateTime.parse(startTime, dateTimeFormatter);
//        LocalDateTime eTime = LocalDateTime.parse(endTime, dateTimeFormatter);

        List<User> playerList = reservationRepository.findUsersByCourtIdAndTime(courtId, startTime, endTime);

        ListResponse<ReservationDetailResponse> reservationDetailResponseList = new ListResponse<>();
        for (User player : playerList) {
            if (user.getId().equals(player.getId())) {
                continue;
            }

            boolean isFollow = followRepository.existsByFollowerAndFollowing(user, player);
            if (isFollow) {
                Follow follow = followRepository.getByFollowerAndFollowing(user, player);
                reservationDetailResponseList.addContents(ReservationDetailResponse.of(player, follow.getId().toString()));
            } else {
                reservationDetailResponseList.addContents(ReservationDetailResponse.of(player, null));
            }
        }

        return reservationDetailResponseList;
    }

    public ListResponse<ReservationByCourtAndDateResponse> getByCourtAndDate(Long courtId, String date) {
        Court court=courtRepository.findById(courtId)
            .orElseThrow(() -> new CourtNotFoundException(
                MessageFormat.format("등록된 농구장을 찾을 수 없습니다. id : {0}", courtId))
            );

        LocalDate dateTime = LocalDate.parse(date, dateTimeFormatter);
        LocalDateTime startTime=dateTime.atStartOfDay();
        LocalDateTime endTime= dateTime.atTime(23,59,59);

        ListResponse<ReservationByCourtAndDateResponse> reservationList = new ListResponse<>();
        reservationRepository.findByCourtIdAndDateBetweenOrderByStartTime(court.getId(),startTime,endTime).stream()
            .map(ReservationByCourtAndDateResponse::of)
            .forEach(reservationList::addContents);

        return reservationList;
    }

}