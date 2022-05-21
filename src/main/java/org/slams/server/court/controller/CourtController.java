package org.slams.server.court.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slams.server.common.api.TokenGetId;
import org.slams.server.court.dto.request.NewCourtInsertRequest;
import org.slams.server.court.dto.request.RequestParamVo;
import org.slams.server.court.dto.response.CourtDetailResponse;
import org.slams.server.court.dto.response.CourtInsertResponseDto;
import org.slams.server.court.service.CourtService;
import org.slams.server.court.service.NewCourtService;
import org.slams.server.user.oauth.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/courts")
public class CourtController {

	private final CourtService courtService;
	private final NewCourtService newCourtService;
	private final Jwt jwt;

	@ApiOperation("사용자가 새 농구장 추가")
	@ApiResponses({
		@ApiResponse(code = 201, message = "추가 성공")
	})
	@PostMapping("/new")
	public ResponseEntity<NewCourtInsertResponse> insert(HttpServletRequest request, @Valid @RequestBody NewCourtInsertRequest newCourtInsertRequest) {
		TokenGetId token = new TokenGetId(request, jwt);
		Long userId = token.getUserId();

		return new ResponseEntity<NewCourtInsertResponse>(newCourtService.insert(newCourtInsertRequest, userId), HttpStatus.CREATED);
	}


	@GetMapping("/detail/{courtId}/{date}/{time}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<CourtDetailResponse> getDetail(
		@PathVariable Long courtId, @PathVariable String date, @PathVariable String time) {
		return ResponseEntity.ok().body(courtService.findDetail(courtId, date, time));
	}

	@GetMapping("/{courtId}/reservations/{date}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Map<String, Object>> getReservationCourts(@PathVariable Long courtId, @PathVariable String date, HttpServletRequest request) {

		TokenGetId token = new TokenGetId(request, jwt);
		Long userId = token.getUserId();


		log.info("userId" + userId);


		Map<String, Object> result = new HashMap<>();
		result.put("courtId", courtId);
		result.put("date", date);
		result.put("reservations", courtService.findCourtReservations(courtId, date, userId));


		// 여기에 추가로 header 토큰 정보가 들어가야 함.
		return ResponseEntity.ok().body(result);
	}


	// 사용자가 날짜, 시간, 바운더리로 농구장 검색하기
	//    /api/v1/courts/date=&{date}&time=${time}&start=${latitude}%${longtitude}&end=${latitude}%${longtitude}
	@GetMapping()
	public ResponseEntity<Map<String, Object>> getAllByDateByBoundary(
		@NotNull RequestParamVo requestParamVo, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<>();
		result.put("courts", courtService.findByDateByBoundary(requestParamVo));

		return ResponseEntity.ok(result);
	}


}
