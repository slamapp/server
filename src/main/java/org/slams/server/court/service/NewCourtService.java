package org.slams.server.court.service;

import lombok.RequiredArgsConstructor;
import org.slams.server.chat.service.ChatroomMappingService;
import org.slams.server.common.api.CursorPageRequest;
import org.slams.server.common.api.CursorPageResponse;
import org.slams.server.court.dto.request.NewCourtInsertRequest;
import org.slams.server.court.dto.response.NewCourtInDoneLookUpResponse;
import org.slams.server.court.dto.response.NewCourtInsertResponse;
import org.slams.server.court.dto.response.NewCourtInReadyLookUpResponse;
import org.slams.server.court.dto.response.NewCourtResponse;
import org.slams.server.court.entity.Court;
import org.slams.server.court.entity.NewCourt;
import org.slams.server.court.entity.Status;
import org.slams.server.court.exception.NewCourtNotFoundException;
import org.slams.server.court.repository.CourtRepository;
import org.slams.server.court.repository.NewCourtRepository;
import org.slams.server.user.entity.User;
import org.slams.server.user.exception.UserNotFoundException;
import org.slams.server.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NewCourtService {

	private final ChatroomMappingService chatroomMappingService;

	private final NewCourtRepository newCourtRepository;
	private final CourtRepository courtRepository;
	private final UserRepository userRepository;

	public CursorPageResponse<List<NewCourtInReadyLookUpResponse>> getNewCourtsInReady(CursorPageRequest cursorPageRequest) {
		PageRequest pageable = PageRequest.of(0, cursorPageRequest.getSize());

		List<NewCourt> newCourts = cursorPageRequest.getIsFirst() ?
			newCourtRepository.findByStatusOrderByIdDesc(List.of(Status.READY), pageable) :
			newCourtRepository.findByStatusLessThanIdOrderByIdDesc(List.of(Status.READY), cursorPageRequest.getLastIdParedForLong(), pageable);

		List<NewCourtInReadyLookUpResponse> newCourtList = new ArrayList<>();
		for (NewCourt newCourt : newCourts) {
			newCourtList.add(
				NewCourtInReadyLookUpResponse.toResponse(newCourt)
			);
		}

		Long lastId = newCourtList.size() < cursorPageRequest.getSize() ? null : newCourts.get(newCourts.size() - 1).getId();

		return new CursorPageResponse<>(newCourtList, lastId.toString());
	}

	public CursorPageResponse<List<NewCourtInDoneLookUpResponse>> getNewCourtsInDone(CursorPageRequest cursorPageRequest) {
		PageRequest pageable = PageRequest.of(0, cursorPageRequest.getSize());

		List<NewCourt> newCourts = cursorPageRequest.getIsFirst() ?
			newCourtRepository.findByStatusOrderByIdDesc(List.of(Status.ACCEPT, Status.DENY), pageable) :
			newCourtRepository.findByStatusLessThanIdOrderByIdDesc(List.of(Status.ACCEPT, Status.DENY), cursorPageRequest.getLastIdParedForLong(), pageable);

		List<NewCourtInDoneLookUpResponse> newCourtList = new ArrayList<>();
		for (NewCourt newCourt : newCourts) {
			newCourtList.add(
				NewCourtInDoneLookUpResponse.toResponse(newCourt)
			);
		}

		Long lastId = newCourtList.size() < cursorPageRequest.getSize() ? null : newCourts.get(newCourts.size() - 1).getId();

		return new CursorPageResponse<>(newCourtList, lastId.toString());
	}

	@Transactional
	public NewCourtInsertResponse insert(NewCourtInsertRequest request, Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException(
				MessageFormat.format("????????? ???????????? ?????? ??? ????????????. id : {0}", id)));

		NewCourt newCourt = request.toEntity(request, user);

		newCourtRepository.save(newCourt);

		return NewCourtInsertResponse.toResponse(newCourt);
	}


	@Transactional
	public NewCourtResponse acceptNewCourt(Long newCourtId, Long supervisorId) {
		NewCourt newCourt = newCourtRepository.findById(newCourtId)
			.orElseThrow(() -> new NewCourtNotFoundException(
				MessageFormat.format("???????????? ????????? ???????????? ?????? ??? ????????????. id : {0}", newCourtId)
			));

		User supervisor = userRepository.findById(supervisorId)
			.orElseThrow(() -> new UserNotFoundException(
				MessageFormat.format("????????? ???????????? ?????? ??? ????????????. id : {0}", supervisorId)
			));

		newCourt.acceptNewCourt(supervisor);

		// ????????????? court save????????? ???????????? ?????????????????? ??????????
		Court court = courtRepository.save(Court.builder()
			.name(newCourt.getName())
			.latitude(newCourt.getLatitude())
			.longitude(newCourt.getLongitude())
			.image(newCourt.getImage())
			.basketCount(newCourt.getBasketCount())
			.texture(newCourt.getTexture())
			.reservations(Collections.emptyList())
			.build());

		// ????????? ??????
		chatroomMappingService.saveChatRoom(court.getId());

		return NewCourtResponse.toResponse(newCourt, supervisor);
	}

	@Transactional
	public NewCourtResponse denyNewCourt(Long newCourtId, Long supervisorId) {
		NewCourt newCourt = newCourtRepository.findById(newCourtId)
			.orElseThrow(() -> new NewCourtNotFoundException(
				MessageFormat.format("???????????? ????????? ???????????? ?????? ??? ????????????. id : {0}", newCourtId)
			));

		User supervisor = userRepository.findById(supervisorId)
			.orElseThrow(() -> new UserNotFoundException(
				MessageFormat.format("????????? ???????????? ?????? ??? ????????????. id : {0}", supervisorId)
			));

		newCourt.denyNewCourt(supervisor);

		return NewCourtResponse.toResponse(newCourt, supervisor);
	}

}
