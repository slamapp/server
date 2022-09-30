package org.slams.server.court.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slams.server.common.annotation.UserId;
import org.slams.server.court.dto.request.NewCourtInsertRequest;
import org.slams.server.court.dto.request.RequestParamVo;
import org.slams.server.court.dto.response.CourtByDateAndBoundaryResponse;
import org.slams.server.court.dto.response.CourtDetailResponse;
import org.slams.server.court.dto.response.NewCourtInsertResponse;
import org.slams.server.court.service.CourtService;
import org.slams.server.court.service.NewCourtService;
import org.slams.server.user.oauth.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
	public ResponseEntity<NewCourtInsertResponse> insert(@UserId Long userId, @Valid @RequestBody NewCourtInsertRequest newCourtInsertRequest) {
		return new ResponseEntity<NewCourtInsertResponse>(
			newCourtService.insert(newCourtInsertRequest, userId), HttpStatus.CREATED);
	}

	@ApiOperation("농구장 상세정보 조회")
	@GetMapping("/{courtId}/detail")
	public ResponseEntity<CourtDetailResponse> getDetail(
		@PathVariable Long courtId, @RequestParam String date, @RequestParam String time) {
		return ResponseEntity.ok(courtService.findDetail(courtId, date, time));
	}

	@ApiOperation("날짜, 시간, 바운더리로 농구장 검색")
	@GetMapping()
	public ResponseEntity<List<CourtByDateAndBoundaryResponse>> getAllByDateAndBoundary(
		@NotNull RequestParamVo requestParamVo) {
		return ResponseEntity.ok(courtService.findByDateAndBoundary(requestParamVo));
	}

}
