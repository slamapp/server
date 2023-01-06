package org.slams.server.reservation.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slams.server.common.annotation.UserId;
import org.slams.server.common.api.CursorPageRequest;
import org.slams.server.common.api.CursorPageResponse;
import org.slams.server.common.api.ListResponse;
import org.slams.server.common.error.ErrorResponse;
import org.slams.server.reservation.dto.request.ReservationInsertRequest;
import org.slams.server.reservation.dto.request.ReservationUpdateRequest;
import org.slams.server.reservation.dto.response.*;
import org.slams.server.reservation.service.ReservationService;
import org.slams.server.user.oauth.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/reservations")
public class ReservationController {

	private final ReservationService reservationService;
	private final Jwt jwt;

	@ApiOperation("예약 추가")
	@ApiResponses({
		@ApiResponse(
			code = 201, message = "추가 성공"
		),
		@ApiResponse(
			code = 400
			, message = "이미 예약이 존재"
			, response = ErrorResponse.class
		)
	})
	@PostMapping()
	public ResponseEntity<ReservationInsertResponse> insert(@Valid @RequestBody ReservationInsertRequest reservationRequest, @UserId Long userId) {
		return new ResponseEntity<ReservationInsertResponse>(
			reservationService.insert(reservationRequest, userId), HttpStatus.CREATED);
	}

	@ApiOperation("예약 수정")
	@ApiResponses({
		@ApiResponse(
			code = 202, message = "수정 성공"
		),
		@ApiResponse(
			code = 403
			, message = "유저가 해당 예약을 수정할 수 있는 권한이 없음"
			, response = ErrorResponse.class
		)
	})
	@PatchMapping("/{reservationId}")
	public ResponseEntity<ReservationUpdateResponse> update(@Valid @RequestBody ReservationUpdateRequest reservationRequest, @PathVariable Long reservationId, @UserId Long userId) {
		return ResponseEntity.accepted()
			.body(reservationService.update(reservationRequest, reservationId, userId));
	}

	@ApiOperation("예약 삭제")
	@ApiResponses({
		@ApiResponse(
			code = 204, message = "삭제 성공"
		),
		@ApiResponse(
			code = 403
			, message = "유저가 해당 예약을 삭제할 수 있는 권한이 없음"
			, response = ErrorResponse.class
		)
	})
	@DeleteMapping("/{reservationId}")
	public ResponseEntity<Void> update(@PathVariable Long reservationId, @UserId Long userId) {
		reservationService.delete(reservationId, userId);

		return ResponseEntity.noContent().build();
	}

	@ApiOperation("다가올 예약목록 조회")
	@GetMapping("/upcoming")
	public ResponseEntity<ListResponse<ReservationUpcomingResponse>> getUpcoming(@UserId Long userId) {
		ListResponse<ReservationUpcomingResponse> response = reservationService.findUpcoming(userId);

		return ResponseEntity.ok().body(response);
	}

	@ApiOperation("지난 예약목록 조회")
	@GetMapping("/expired")
	public ResponseEntity<CursorPageResponse<List<ReservationExpiredResponse>>> getExpired(CursorPageRequest cursorPageRequest, @UserId Long userId) {
		CursorPageResponse<List<ReservationExpiredResponse>> response = reservationService.findExpired(userId, cursorPageRequest);

		return ResponseEntity.ok(response);
	}

	@ApiOperation("예약 상세보기")
	@GetMapping("/{courtId}")
	public ResponseEntity<ListResponse<ReservationDetailResponse>> getDetail(@PathVariable Long courtId, @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime, @UserId Long userId) {
		ListResponse<ReservationDetailResponse> response = reservationService.getDetail(userId, courtId, startTime, endTime);

		return ResponseEntity.ok(response);
	}

	@ApiOperation("특정 날짜의 농구장 예약 전체조회")
	@GetMapping()
	public ResponseEntity<ListResponse<ReservationByCourtAndDateResponse>> getByCourtAndDate(@RequestParam Long courtId, @RequestParam String date, @UserId Long userId) {
		ListResponse<ReservationByCourtAndDateResponse> response = reservationService.getByCourtAndDate(courtId, date);

		return ResponseEntity.ok(response);
	}

}
