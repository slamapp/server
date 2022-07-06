package org.slams.server.reservation.service;

import lombok.RequiredArgsConstructor;
import org.slams.server.common.api.CursorPageRequest;
import org.slams.server.common.api.CursorPageResponse;
import org.slams.server.common.api.ListResponse;
import org.slams.server.common.error.exception.ErrorCode;
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
import org.slams.server.user.exception.UserNotFoundException;
import org.slams.server.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReservationService {

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

		reservationRepository.findByCourtAndUser(court, user)
			.ifPresent(reservation -> { // 동일한 유저가 같은 코트를 예약할 수 없다
				throw new ReservationAlreadyExistException(
					MessageFormat.format("이미 저장된 예약이 있습니다. id : {0}", reservation.getId())
				);
			});

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


    // getDetailByReservationByUser
    @Transactional
    public List<ReservationResponseDto> getDetailByReservationByUser(Long userId, Long courtId, String startTime, String endTime) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_EXIST_MEMBER.getMessage()));


        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime sTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime eTime = LocalDateTime.parse(endTime, formatter);


        // reservationId -> User 검색 ->
        List<User> byIdLists = reservationRepository.findByReservation(courtId, sTime, eTime);

        List<ReservationResponseDto> reservationResponseDtoList=new ArrayList<>();
        Boolean isFollow=false;


        for (User foundUser:byIdLists) {
            User joinUser = foundUser;

            if (user.getId() != joinUser.getId()) {
                isFollow = followRepository.existsByFollowerAndFollowing(user, foundUser);
                if (isFollow) {
                    Optional<Follow> follow=followRepository.findByFollowerAndFollowing(user,foundUser);
                    reservationResponseDtoList.add(new ReservationResponseDto(joinUser, isFollow,follow.get()));
                }
                else {
                    reservationResponseDtoList.add(new ReservationResponseDto(joinUser, isFollow));
                }

            }
        }

        return reservationResponseDtoList;
    }



    @Transactional
    public CursorPageResponse<List<ReservationExpiredResponseDto>> findExpired(Long userId, CursorPageRequest cursorPageRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        MessageFormat.format("가입한 사용자를 찾을 수 없습니다. id : {0}", userId)));

        LocalDateTime localDateTime=LocalDateTime.now();

//        reservationRepository.findByUserByExpiredOrderByDesc(userId,localDateTime);

        PageRequest pageable = PageRequest.of(0, cursorPageRequest.getSize());
        List<Reservation> reservations = cursorPageRequest.getIsFirst() ?
                reservationRepository.findByUserByExpiredOrderByDesc(userId, localDateTime,pageable) :
                reservationRepository.findByUserByAndIdLessThanExpiredOrderByDesc(cursorPageRequest.getLastIdParedForLong(), pageable);

        List<ReservationExpiredResponseDto> reservationExpiredResponseDtoList = new ArrayList<>();

//        int reservationSize= reservationExpiredResponseDtoList.size();

        for (Reservation reservation : reservations) {

            // todo. 여기서 한번 더 뒤지면서 reservationSize를 센다.
            Long reservationSize = reservationRepository.countUserByCourtAndTime(reservation.getCourt().getId(), reservation.getStartTime(), reservation.getEndTime());
            reservationExpiredResponseDtoList.add(
                    ReservationExpiredResponseDto.toResponse(
                            reservation, reservation.getCourt(), reservation.getCreatedAt(), reservation.getUpdatedAt(),reservationSize)
            );
        }

        Long lastId = reservations.size() < cursorPageRequest.getSize() ? null : reservations.get(reservations.size() - 1).getId();

        return new CursorPageResponse<>(reservationExpiredResponseDtoList, lastId.toString());
    }


//    public List<CourtReservationResponseDto> findCourtReservationsByDate(Long courtId, String date) {
//        Court court=courtRepository.findById(courtId)
//            .orElseThrow(() -> new CourtNotFoundException(
//                MessageFormat.format("등록된 농구장을 찾을 수 없습니다. id : {0}", courtId)));
//
//        LocalDate dateTime = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
//        LocalDateTime startLocalDateTime=dateTime.atStartOfDay();
//        LocalDateTime endLocalDateTime= dateTime.atTime(23,59,59);
//
//        return reservationRepository.findByIdAndDate(courtId,startLocalDateTime,endLocalDateTime).stream()
//            .map(CourtReservationResponseDto::new)
//            .collect(Collectors.toList());
//    }



}
