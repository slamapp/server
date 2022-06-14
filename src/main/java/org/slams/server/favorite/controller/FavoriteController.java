package org.slams.server.favorite.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slams.server.common.api.TokenGetId;
import org.slams.server.common.error.ErrorResponse;
import org.slams.server.favorite.dto.request.FavoriteInsertRequest;
import org.slams.server.favorite.dto.response.FavoriteInsertResponse;
import org.slams.server.favorite.dto.response.FavoriteLookUpResponse;
import org.slams.server.favorite.service.FavoriteService;
import org.slams.server.user.oauth.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final Jwt jwt;

    @ApiOperation("즐겨찾기 추가")
    @ApiResponses({
        @ApiResponse(
            code = 201, message = "추가 성공"
        ),
        @ApiResponse(
            code = 400
            , message = "존재하지 않는 농구장에 접근"
            , response = ErrorResponse.class
        )
    })
    @PostMapping()
    public ResponseEntity<FavoriteInsertResponse> insert(@RequestBody FavoriteInsertRequest favoriteInsertRequest, HttpServletRequest request) {

        TokenGetId token=new TokenGetId(request,jwt);
        Long userId=token.getUserId();

        return new ResponseEntity<FavoriteInsertResponse>(
            favoriteService.insert(favoriteInsertRequest, userId), HttpStatus.CREATED);
    }

	@ApiOperation("즐겨칮기 목록 조회")
	@GetMapping()
	public ResponseEntity<List<FavoriteLookUpResponse>> getAll(HttpServletRequest request) {
		TokenGetId token = new TokenGetId(request, jwt);
		Long userId = token.getUserId();

		return ResponseEntity.ok().body(favoriteService.getAll(userId));
	}

	@ApiOperation("즐겨찾기 삭제(취소)")
	@ApiResponses({
		@ApiResponse(code = 204, message = "삭제(취소) 성공")
	})
    @DeleteMapping("{favoriteId}")
    public ResponseEntity<Void> delete(@PathVariable Long favoriteId, HttpServletRequest request) {
        TokenGetId token=new TokenGetId(request,jwt);
        Long userId=token.getUserId();

        favoriteService.delete(favoriteId);

        return ResponseEntity.noContent().build();
    }







}
