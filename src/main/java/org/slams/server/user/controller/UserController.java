package org.slams.server.user.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slams.server.common.annotation.UserId;
import org.slams.server.common.error.ErrorResponse;
import org.slams.server.user.dto.request.ExtraUserInfoRequest;
import org.slams.server.user.dto.response.*;
import org.slams.server.user.oauth.jwt.Jwt;
import org.slams.server.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;
	private final Jwt jwt;

	@ApiOperation("로그인 시 기본 데이터(사용자 정보, 알림 정보) 조회")
	@GetMapping("/me")
	public ResponseEntity<DefaultUserInfoResponse> getDefaultInfo(@UserId Long userId) {
		DefaultUserInfoResponse defaultUserInfoResponse = userService.getDefaultInfo(userId);

		return ResponseEntity.ok(defaultUserInfoResponse);
	}

	@ApiOperation("내 프로필 정보 조회")
	@GetMapping(value = "/myprofile", produces = "application/json; charset=utf-8;")
	public ResponseEntity<MyProfileLookUpResponse> getMyInfo(@UserId Long userId) {
		MyProfileLookUpResponse myProfileLookUpResponse = userService.getMyInfo(userId);

		return ResponseEntity.ok(myProfileLookUpResponse);
	}

	@ApiResponses({
		@ApiResponse(
			code = 200
			, message = "조회 성공"
		),
		@ApiResponse(
			code = 400
			, message = "존재하지 않는 유저에 접근"
			, response = ErrorResponse.class
		)
	})
	@GetMapping("/{userId}")
	public ResponseEntity<UserProfileLookUpResponse> getUserInfo(@UserId Long myId, @PathVariable Long userId) {
		UserProfileLookUpResponse userProfileLookUpResponse = userService.getUserInfo(myId, userId);

		return ResponseEntity.ok(userProfileLookUpResponse);
	}

	@ApiOperation("내 정보 수정(추가 입력)")
	@PutMapping("/myprofile")
	public ResponseEntity<MyProfileUpdateResponse> addExtraUserInfo(@RequestBody ExtraUserInfoRequest extraUserInfoRequest, @UserId Long userId) {
		MyProfileUpdateResponse myProfileUpdateResponse = userService.addExtraUserInfo(userId, extraUserInfoRequest);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(myProfileUpdateResponse);
	}

	@ApiOperation("내 프로필 이미지 수정")
	@PutMapping("/myprofile/image")
	public ResponseEntity<ProfileImageResponse> updateUserProfileImage(
		@RequestPart("profileImage") MultipartFile profileImageRequest, @UserId Long userId) throws IOException {
		ProfileImageResponse profileImageResponse = userService.updateUserProfileImage(userId, profileImageRequest);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(profileImageResponse);
	}

	@ApiOperation("내 프로필 이미지 삭제")
	@DeleteMapping("/myprofile/image")
	public ResponseEntity<Void> deleteUserProfileImage(@UserId Long userId) {
		userService.deleteUserProfileImage(userId);

		return ResponseEntity.noContent().build();
	}

}
