package org.slams.server.user.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slams.server.common.error.ErrorResponse;
import org.slams.server.user.dto.request.ExtraUserInfoRequest;
import org.slams.server.user.dto.response.*;
import org.slams.server.user.exception.InvalidTokenException;
import org.slams.server.user.oauth.jwt.Jwt;
import org.slams.server.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;
	private final Jwt jwt;

	@ApiOperation("로그인 시 기본 데이터(사용자 정보, 알림 정보) 조회")
	@GetMapping("/me")
	public ResponseEntity<DefaultUserInfoResponse> getDefaultInfo(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		String[] tokenString = authorization.split(" ");
		if (!tokenString[0].equals("Bearer")) {
			throw new InvalidTokenException("토큰 정보가 올바르지 않습니다.");
		}

		Jwt.Claims claims = jwt.verify(tokenString[1]);

		DefaultUserInfoResponse defaultUserInfoResponse = userService.getDefaultInfo(claims.getUserId());

		return ResponseEntity.ok(defaultUserInfoResponse);
	}

	@ApiOperation("내 프로필 정보 조회")
	@GetMapping(value = "/myprofile", produces = "application/json; charset=utf-8;")
	public ResponseEntity<MyProfileLookUpResponse> getMyInfo(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		String[] tokenString = authorization.split(" ");
		if (!tokenString[0].equals("Bearer")) {
			throw new InvalidTokenException("토큰 정보가 올바르지 않습니다.");
		}

		Jwt.Claims claims = jwt.verify(tokenString[1]);

		MyProfileLookUpResponse myProfileLookUpResponse = userService.getMyInfo(claims.getUserId());

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
	public ResponseEntity<UserProfileLookUpResponse> getUserInfo(HttpServletRequest request, @PathVariable Long userId) {
		String authorization = request.getHeader("Authorization");
		String[] tokenString = authorization.split(" ");
		if (!tokenString[0].equals("Bearer")) {
			throw new InvalidTokenException("토큰 정보가 올바르지 않습니다.");
		}

		Jwt.Claims claims = jwt.verify(tokenString[1]);

		UserProfileLookUpResponse userProfileLookUpResponse = userService.getUserInfo(claims.getUserId(), userId);

		return ResponseEntity.ok(userProfileLookUpResponse);
	}

	@ApiOperation("내 정보 수정(추가 입력)")
	@PutMapping("/myprofile")
	public ResponseEntity<MyProfileUpdateResponse> addExtraUserInfo(HttpServletRequest request, @RequestBody ExtraUserInfoRequest extraUserInfoRequest) {
		String authorization = request.getHeader("Authorization");
		String[] tokenString = authorization.split(" ");
		if (!tokenString[0].equals("Bearer")) {
			throw new InvalidTokenException("토큰 정보가 올바르지 않습니다.");
		}

		Jwt.Claims claims = jwt.verify(tokenString[1]);

		MyProfileUpdateResponse myProfileUpdateResponse = userService.addExtraUserInfo(claims.getUserId(), extraUserInfoRequest);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(myProfileUpdateResponse);
	}

	@ApiOperation("내 프로필 이미지 수정")
	@PutMapping("/myprofile/image")
	public ResponseEntity<ProfileImageResponse> updateUserProfileImage(
		HttpServletRequest request, @RequestPart("profileImage") MultipartFile profileImageRequest) throws IOException {
		String authorization = request.getHeader("Authorization");
		String[] tokenString = authorization.split(" ");
		if (!tokenString[0].equals("Bearer")) {
			throw new InvalidTokenException("토큰 정보가 올바르지 않습니다.");
		}

		Jwt.Claims claims = jwt.verify(tokenString[1]);

		ProfileImageResponse profileImageResponse = userService.updateUserProfileImage(claims.getUserId(), profileImageRequest);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(profileImageResponse);
	}

	@ApiOperation("내 프로필 이미지 삭제")
	@DeleteMapping("/myprofile/image")
	public ResponseEntity<Void> deleteUserProfileImage(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		String[] tokenString = authorization.split(" ");
		if (!tokenString[0].equals("Bearer")) {
			throw new InvalidTokenException("토큰 정보가 올바르지 않습니다.");
		}

		Jwt.Claims claims = jwt.verify(tokenString[1]);

		userService.deleteUserProfileImage(claims.getUserId());

		return ResponseEntity.noContent().build();
	}

}
