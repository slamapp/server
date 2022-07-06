package org.slams.server.reservation.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slams.server.common.api.CursorPageRequest;
import org.slams.server.common.api.CursorPageResponse;
import org.slams.server.common.api.ListResponse;
import org.slams.server.common.api.TokenGetId;
import org.slams.server.common.error.ErrorResponse;
import org.slams.server.reservation.dto.request.ReservationInsertRequest;
import org.slams.server.reservation.dto.request.ReservationUpdateRequest;
import org.slams.server.reservation.dto.response.ReservationExpiredResponseDto;
import org.slams.server.reservation.dto.response.ReservationInsertResponse;
import org.slams.server.reservation.dto.response.ReservationUpcomingResponse;
import org.slams.server.reservation.dto.response.ReservationUpdateResponse;
import org.slams.server.reservation.service.ReservationService;
import org.slams.server.user.oauth.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<ReservationInsertResponse> insert(@Valid @RequestBody ReservationInsertRequest reservationRequest, HttpServletRequest request) {

        TokenGetId token=new TokenGetId(request,jwt);
        Long userId=token.getUserId();

        return new ResponseEntity<ReservationInsertResponse>(reservationService.insert(reservationRequest, userId), HttpStatus.CREATED);
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
    public ResponseEntity<ReservationUpdateResponse> update(@Valid @RequestBody ReservationUpdateRequest reservationRequest, @PathVariable Long reservationId, HttpServletRequest request) {
        TokenGetId token = new TokenGetId(request, jwt);
        Long userId = token.getUserId();

        return ResponseEntity.accepted().body(reservationService.update(reservationRequest, reservationId, userId));
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
    public ResponseEntity<Void> update(@PathVariable Long reservationId, HttpServletRequest request) {
        TokenGetId token = new TokenGetId(request, jwt);
        Long userId = token.getUserId();

        reservationService.delete(reservationId, userId);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation("다가올 예약목록 조회")
    @GetMapping("/upcoming")
    public ResponseEntity<ListResponse<ReservationUpcomingResponse>> getUpcoming(HttpServletRequest request) {
        TokenGetId token = new TokenGetId(request, jwt);
        Long userId = token.getUserId();

        ListResponse<ReservationUpcomingResponse> response = reservationService.findUpcoming(userId);

        return ResponseEntity.ok().body(response);
    }

    // 토글 상세 조회 API
    // /api/v1/reservations/{reservationId} -> 변경 api/v1/reservations/{courtId}/{startTIme}/{endTime}
    @GetMapping("/{courtId}/{startTime}/{endTime}")
    public ResponseEntity<Map<String,Object>>getDetail(HttpServletRequest request, @PathVariable Long courtId, @PathVariable String startTime, @PathVariable String endTime) {
        TokenGetId token=new TokenGetId(request,jwt);
        Long userId=token.getUserId();

        Map<String,Object>result=new HashMap<>();
        result.put("participants",reservationService.getDetailByReservationByUser(userId,courtId,startTime,endTime));
        return ResponseEntity.ok().body(result);
    }

    // 지난 예약 조회
//    @GetMapping("/expired/{reservationId}")
//    public ResponseEntity<CursorPageResponse<List<ReservationExpiredResponseDto>>> getExpired(
//            @PathVariable Long reservationId, CursorPageRequest cursorPageRequest, HttpServletRequest request) {
//
//        TokenGetId token=new TokenGetId(request,jwt);
//        Long userId=token.getUserId();
//        CursorPageResponse<List<ReservationExpiredResponseDto>> followerResponse = reservationService.findExpired(userId, cursorPageRequest,reservationId);
//
//        return ResponseEntity.ok(followerResponse);
//    }

    @GetMapping("/expired")
    public ResponseEntity<CursorPageResponse<List<ReservationExpiredResponseDto>>> getExpired(
            CursorPageRequest cursorPageRequest, HttpServletRequest request) {

        TokenGetId token=new TokenGetId(request,jwt);
        Long userId=token.getUserId();
        CursorPageResponse<List<ReservationExpiredResponseDto>> followerResponse = reservationService.findExpired(userId, cursorPageRequest);

        return ResponseEntity.ok(followerResponse);
    }


//    @ApiOperation("특정 날짜의 농구장 예약 전체조회")
//    @GetMapping("/{courtId}/reservations")
//    public ResponseEntity<Map<String, Object>> getCourtReservationsByDate(@PathVariable Long courtId, @RequestParam String date, HttpServletRequest request) {
//        TokenGetId token = new TokenGetId(request, jwt);
//        Long userId = token.getUserId();
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("courtId", courtId);
//        result.put("date", date);
//        result.put("reservations", courtService.findCourtReservationsByDate(courtId, date));
//
//
//        // 여기에 추가로 header 토큰 정보가 들어가야 함.
//        return ResponseEntity.ok().body(result);
//    }

}
