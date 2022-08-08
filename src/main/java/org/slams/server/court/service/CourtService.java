package org.slams.server.court.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slams.server.common.utils.AwsS3Uploader;
import org.slams.server.court.dto.request.RequestParamVo;
import org.slams.server.court.dto.request.TimeEnum;
import org.slams.server.court.dto.response.*;
import org.slams.server.court.entity.Court;
import org.slams.server.court.repository.CourtRepository;
import org.slams.server.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slams.server.court.exception.*;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Transactional(readOnly = true)
@AllArgsConstructor
@Service
public class CourtService {

	private final CourtRepository courtRepository;
	private final AwsS3Uploader awsS3Uploader;
	private final ReservationRepository reservationRepository;

	public CourtDetailResponse findDetail(Long courtId, String date, String time) {
		Court court = courtRepository.findById(courtId)
			.orElseThrow(() -> new CourtNotFoundException(
				MessageFormat.format("등록된 농구장을 찾을 수 없습니다. id : {0}", courtId)));

		List<LocalDateTime> localDateTimes = changeTimeZone(date, time);
		LocalDateTime startLocalDateTime = localDateTimes.get(0);
		LocalDateTime endLocalDateTime = localDateTimes.get(1);

		Long reservationMaxCount = reservationRepository.countUserByCourtAndTime(courtId, startLocalDateTime, endLocalDateTime);

		return CourtDetailResponse.toResponse(court, reservationMaxCount);
	}

	public List<CourtByDateAndBoundaryResponse> findByDateAndBoundary(RequestParamVo requestParamVo) {
		String date = requestParamVo.getDate();
		String time = requestParamVo.getTime();

		List<LocalDateTime> localDateTimes = changeTimeZone(date, time);
		LocalDateTime startLocalDateTime = localDateTimes.get(0);
		LocalDateTime endLocalDateTime = localDateTimes.get(1);

		List<Double> latitudes = requestParamVo.getLatitude();
		Collections.sort(latitudes);
		double startLatitude = latitudes.get(0);
		double endLatitude = latitudes.get(1);

		List<Double> longitudes = requestParamVo.getLongitude();
		Collections.sort(longitudes);
		double startLongitude = longitudes.get(0);
		double endLongitude = longitudes.get(1);

		List<Court> courtsByBoundary = courtRepository.findByBoundary(startLatitude, endLatitude, startLongitude, endLongitude);

		List<CourtByDateAndBoundaryResponse> courtByDateAndBoundaryResponseList = new ArrayList<>();
		for (Court court : courtsByBoundary) {
			Long reservations = reservationRepository.countUserByCourtAndTime(court.getId(), startLocalDateTime, endLocalDateTime);

			courtByDateAndBoundaryResponseList.add(CourtByDateAndBoundaryResponse.toResponse(court, reservations));
		}

		return courtByDateAndBoundaryResponseList;
	}

	private List<LocalDateTime> changeTimeZone(String date, String time) {

		LocalDate dateTime = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
		List<LocalDateTime> dateTimeList = new ArrayList<>();
		LocalDateTime startLocalDateTime;
		LocalDateTime endLocalDateTime;

		TimeEnum timeEnum = TimeEnum.valueOf(time.toUpperCase());
		switch (timeEnum) {
			case DAWN:
				startLocalDateTime = dateTime.atTime(0, 0, 0);
				endLocalDateTime = dateTime.atTime(5, 59, 59);
				break;
			case MORNING:
				startLocalDateTime = dateTime.atTime(6, 0, 0);
				endLocalDateTime = dateTime.atTime(11, 59, 59);
				break;
			case AFTERNOON:
				startLocalDateTime = dateTime.atTime(12, 0, 0);
				endLocalDateTime = dateTime.atTime(17, 59, 59);
				break;
			case NIGHT:
				startLocalDateTime = dateTime.atTime(18, 0, 0);
				endLocalDateTime = dateTime.atTime(23, 59, 59);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + timeEnum);
		}

		dateTimeList.add(startLocalDateTime);
		dateTimeList.add(endLocalDateTime);

		return dateTimeList;
	}

}
