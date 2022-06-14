package org.slams.server.favorite.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slams.server.common.error.exception.ErrorCode;
import org.slams.server.court.entity.Court;
import org.slams.server.court.exception.CourtNotFoundException;
import org.slams.server.court.repository.CourtRepository;
import org.slams.server.favorite.dto.request.FavoriteInsertRequest;
import org.slams.server.favorite.dto.response.FavoriteDeleteResponseDto;
import org.slams.server.favorite.dto.response.FavoriteInsertResponse;
import org.slams.server.favorite.dto.response.FavoriteLookUpResponseDto;
import org.slams.server.favorite.entity.Favorite;
import org.slams.server.favorite.repository.FavoriteRepository;
import org.slams.server.user.entity.User;
import org.slams.server.user.exception.UserNotFoundException;
import org.slams.server.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final CourtRepository courtRepository;


    @Transactional
    public FavoriteInsertResponse insert(FavoriteInsertRequest favoriteInsertRequest, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(
                MessageFormat.format("가입한 사용자를 찾을 수 없습니다. id : {0}", userId)));
        Court court = courtRepository.findById(Long.parseLong(favoriteInsertRequest.getCourtId()))
            .orElseThrow(() -> new CourtNotFoundException(
                MessageFormat.format("등록된 농구장을 찾을 수 없습니다. id : {0}", Long.parseLong(favoriteInsertRequest.getCourtId()))));

        return FavoriteInsertResponse.toResponse(
            favoriteRepository.findByCourtAndUser(court, user)
                .map(favorite -> {
                    log.warn("Already exists : favorite-courtId({}), userId({})", court.getId(), user.getId());
                    return favorite;
                })
                .orElseGet(() -> {
                    Favorite favorite = favoriteInsertRequest.toEntity(court, user);
                    favoriteRepository.save(favorite);
                    return favorite;
                })
        );
    }


    // 내가 즐겨찾기 한 코트 검색
    @Transactional
    public List<FavoriteSelectResponseDto> getAll(Long userId) {
        User user =userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        MessageFormat.format("가입한 사용자를 찾을 수 없습니다. id : {0}", userId)));

        return favoriteRepository.findAllByUser(user).stream()
                .map(FavoriteSelectResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public FavoriteDeleteResponseDto delete(Long userId, Long favoriteId) {
        User user =userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        MessageFormat.format("가입한 사용자를 찾을 수 없습니다. id : {0}", userId)));

        Favorite reservation= favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new CourtNotFoundException(ErrorCode.NOT_EXIST_FAVORITE.getMessage()));

        favoriteRepository.delete(reservation);
        return new FavoriteDeleteResponseDto(favoriteId);

    }


}
