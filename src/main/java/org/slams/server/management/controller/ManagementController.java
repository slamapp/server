package org.slams.server.management.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slams.server.common.annotation.UserId;
import org.slams.server.common.api.CursorPageRequest;
import org.slams.server.common.api.CursorPageResponse;
import org.slams.server.court.dto.request.NewCourtRequest;
import org.slams.server.court.dto.response.NewCourtInDoneLookUpResponse;
import org.slams.server.court.dto.response.NewCourtInReadyLookUpResponse;
import org.slams.server.court.dto.response.NewCourtResponse;
import org.slams.server.court.service.NewCourtService;
import org.slams.server.user.oauth.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/management")
public class ManagementController {

	private final NewCourtService newCourtService;
	private final Jwt jwt;

	@ApiOperation("사용자가 추가한 농구장 중에서 처리 대기 중인 농구장 목록 조회")
	@GetMapping("/newCourts/ready")
	public ResponseEntity<CursorPageResponse<List<NewCourtInReadyLookUpResponse>>> getNewCourtsInReady(CursorPageRequest cursorPageRequest) {
		CursorPageResponse<List<NewCourtInReadyLookUpResponse>> newCourtResponse
			= newCourtService.getNewCourtsInReady(cursorPageRequest);

		return ResponseEntity.ok(newCourtResponse);
	}

	@ApiOperation("사용자가 추가한 농구장 중에서 처리 완료된 농구장 목록 조회")
	@GetMapping("/newCourts/done")
	public ResponseEntity<CursorPageResponse<List<NewCourtInDoneLookUpResponse>>> getNewCourtsInDone(CursorPageRequest cursorPageRequest) {
		CursorPageResponse<List<NewCourtInDoneLookUpResponse>> newCourtResponse
			= newCourtService.getNewCourtsInDone(cursorPageRequest);

		return ResponseEntity.ok(newCourtResponse);
	}

	@ApiOperation("농구장 등록 승인")
	@PatchMapping("/newCourt/accept")
	public ResponseEntity<NewCourtResponse> accept(@RequestBody NewCourtRequest newCourtRequest, @UserId Long userId) {
		return ResponseEntity.status(HttpStatus.ACCEPTED)
			.body(newCourtService.acceptNewCourt(Long.parseLong(newCourtRequest.getNewCourtId()), userId));
	}

	@ApiOperation("농구장 등록 거절")
	@PatchMapping("/newCourt/deny")
	public ResponseEntity<NewCourtResponse> deny(@RequestBody NewCourtRequest newCourtRequest, @UserId Long userId) {
		return ResponseEntity.status(HttpStatus.ACCEPTED)
			.body(newCourtService.denyNewCourt(Long.parseLong(newCourtRequest.getNewCourtId()), userId));
	}

}
