package org.slams.server.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slams.server.common.api.CursorPageRequest;
import org.slams.server.common.api.CursorPageResponse;
import org.slams.server.court.dto.request.NewCourtRequest;
import org.slams.server.court.dto.response.NewCourtInDoneLookUpResponse;
import org.slams.server.court.dto.response.NewCourtInReadyLookUpResponse;
import org.slams.server.court.dto.response.NewCourtResponse;
import org.slams.server.court.entity.NewCourt;
import org.slams.server.court.entity.Status;
import org.slams.server.court.entity.Texture;
import org.slams.server.court.service.NewCourtService;
import org.slams.server.user.entity.Role;
import org.slams.server.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
class ManagementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private NewCourtService newCourtService;

	private String jwtToken;

	@BeforeEach
	void setUp() throws IOException {
		// 컨테이너 생성
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();

		// 환경변수 관리 객체 생성
		ConfigurableEnvironment env = ctx.getEnvironment();

		// 프로퍼티 관리 객체 생성
		MutablePropertySources prop = env.getPropertySources();

		// 프로퍼티 관리 객체에 프로퍼티 파일 추가
		prop.addLast(new ResourcePropertySource("classpath:test.properties"));

		// 프로퍼티 정보 얻기
		String token = env.getProperty("token");
		jwtToken = "Bearer " + token;
	}

	@Test
	void getNewCourtsInReady() throws Exception {
		// given
		CursorPageRequest request = new CursorPageRequest(3, 5L, false);

		NewCourt newCourt = NewCourt.builder()
			.id(1L)
			.name("관악구민운동장 농구장")
			.latitude(38.987654)
			.longitude(12.309472)
			.image("aHR0cHM6Ly9pYmIuY28vcXMwSnZXYg")
			.texture(Texture.ASPHALT)
			.basketCount(2)
			.status(Status.READY)
			.proposer(User.builder()
				.id(1L)
				.nickname("sally")
				.profileImage("s3에 저장된 사용자 프로필 이미지 url")
				.role(Role.USER).build())
			.createdAt(LocalDateTime.now())
			.updateAt(LocalDateTime.now())
			.build();

		List<NewCourtInReadyLookUpResponse> newCourts = List.of(NewCourtInReadyLookUpResponse.toResponse(newCourt));

		CursorPageResponse<List<NewCourtInReadyLookUpResponse>> response = new CursorPageResponse<>(newCourts, 5L);

		given(newCourtService.getNewCourtsInReady(any())).willReturn(response);

		// when
		ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/management/newCourts/ready")
				.header("Authorization", jwtToken)
				.param("size", String.valueOf(request.getSize()))
				.param("lastId", String.valueOf(request.getLastId()))
				.param("isFirst", request.getIsFirst().toString())
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

		// then
		resultActions.andExpect(status().isOk())
			.andDo(document("management/newCourt-getNewCourtsInReady", preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestParameters(
					parameterWithName("size").description("요청할 데이터의 수"),
					parameterWithName("lastId").description("화면에 보여준 마지막 데이터의 구별키"),
					parameterWithName("isFirst").description("처음으로 요청했는지 여부")
				),
				responseFields(
					fieldWithPath("contents").type(JsonFieldType.ARRAY).description("사용자가 추가한 농구장 목록"),
					fieldWithPath("contents[].newCourt").type(JsonFieldType.OBJECT).description("사용자가 추가한 농구장"),
					fieldWithPath("contents[].newCourt.id").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 구별키"),
					fieldWithPath("contents[].newCourt.name").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 이름"),
					fieldWithPath("contents[].newCourt.latitude").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 위도"),
					fieldWithPath("contents[].newCourt.longitude").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 경도"),
					fieldWithPath("contents[].newCourt.image").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 이미지"),
					fieldWithPath("contents[].newCourt.texture").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 바닥 재질"),
					fieldWithPath("contents[].newCourt.basketCount").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 골대 수"),
					fieldWithPath("contents[].newCourt.status").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 승인여부"),
					fieldWithPath("contents[].newCourt.createdAt").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 정보 최초 생성시간"),
					fieldWithPath("contents[].newCourt.updatedAt").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 정보 최근 수정시간"),
					fieldWithPath("contents[].creator").type(JsonFieldType.OBJECT).description("농구장 추가를 요청한 사용자"),
					fieldWithPath("contents[].creator.id").type(JsonFieldType.STRING).description("농구장 추가를 요청한 사용자 구별키"),
					fieldWithPath("contents[].creator.nickname").type(JsonFieldType.STRING).description("농구장 추가를 요청한 사용자 닉네임"),
					fieldWithPath("contents[].creator.profileImage").type(JsonFieldType.STRING).description("농구장 추가를 요청한 사용자 프로필 이미지"),
					fieldWithPath("contents[].creator.role").type(JsonFieldType.STRING).description("농구장 추가를 요청한 사용자 권한"),
					fieldWithPath("lastId").type(JsonFieldType.NUMBER).description("서버에서 제공한 마지막 데이터의 구별키").optional()
				)
			));
	}

	@Test
	void getNewCourtsInDone() throws Exception {
		// given
		CursorPageRequest request = new CursorPageRequest(3, 5L, false);

		User creator = User.builder()
			.id(1L)
			.nickname("sally")
			.profileImage("s3에 저장된 사용자 프로필 이미지 url")
			.role(Role.USER).build();
		NewCourt acceptedCourt = NewCourt.builder()
			.id(1L)
			.name("관악구민운동장 농구장")
			.latitude(38.987654)
			.longitude(12.309472)
			.image("aHR0cHM6Ly9pYmIuY28vcXMwSnZXYg")
			.texture(Texture.ASPHALT)
			.basketCount(2)
			.proposer(creator)
			.createdAt(LocalDateTime.now())
			.updateAt(LocalDateTime.now())
			.build();
		NewCourt deniedCourt = NewCourt.builder()
			.id(2L)
			.name("바닷속 농구장")
			.latitude(24.987654)
			.longitude(77.273472)
			.image("aHR0cHM6Ly9pYmIuY28vcXMwSnZXYg")
			.texture(Texture.ASPHALT)
			.basketCount(2)
			.proposer(creator)
			.createdAt(LocalDateTime.now())
			.updateAt(LocalDateTime.now())
			.build();

		acceptedCourt.acceptNewCourt(creator);
		deniedCourt.denyNewCourt(creator);

		List<NewCourtInDoneLookUpResponse> newCourts = List.of(
			NewCourtInDoneLookUpResponse.toResponse(deniedCourt), NewCourtInDoneLookUpResponse.toResponse(acceptedCourt));

		CursorPageResponse<List<NewCourtInDoneLookUpResponse>> response = new CursorPageResponse<>(newCourts, 5L);

		given(newCourtService.getNewCourtsInDone(any())).willReturn(response);

		// when
		ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/management/newCourts/done")
				.header("Authorization", jwtToken)
				.param("size", String.valueOf(request.getSize()))
				.param("lastId", String.valueOf(request.getLastId()))
				.param("isFirst", request.getIsFirst().toString())
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

		// then
		resultActions.andExpect(status().isOk())
			.andDo(document("management/newCourt-getNewCourtsInDone", preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestParameters(
					parameterWithName("size").description("요청할 데이터의 수"),
					parameterWithName("lastId").description("화면에 보여준 마지막 데이터의 구별키"),
					parameterWithName("isFirst").description("처음으로 요청했는지 여부")
				),
				responseFields(
					fieldWithPath("contents").type(JsonFieldType.ARRAY).description("사용자가 추가한 농구장 목록"),
					fieldWithPath("contents[].newCourt").type(JsonFieldType.OBJECT).description("사용자가 추가한 농구장"),
					fieldWithPath("contents[].newCourt.id").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 구별키"),
					fieldWithPath("contents[].newCourt.name").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 이름"),
					fieldWithPath("contents[].newCourt.latitude").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 위도"),
					fieldWithPath("contents[].newCourt.longitude").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 경도"),
					fieldWithPath("contents[].newCourt.image").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 이미지"),
					fieldWithPath("contents[].newCourt.texture").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 바닥 재질"),
					fieldWithPath("contents[].newCourt.basketCount").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 골대 수"),
					fieldWithPath("contents[].newCourt.status").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 승인여부"),
					fieldWithPath("contents[].newCourt.createdAt").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 정보 최초 생성시간"),
					fieldWithPath("contents[].newCourt.updatedAt").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 정보 최근 수정시간"),
					fieldWithPath("contents[].creator").type(JsonFieldType.OBJECT).description("농구장 추가를 요청한 사용자"),
					fieldWithPath("contents[].creator.id").type(JsonFieldType.STRING).description("농구장 추가를 요청한 사용자 구별키"),
					fieldWithPath("contents[].creator.nickname").type(JsonFieldType.STRING).description("농구장 추가를 요청한 사용자 닉네임"),
					fieldWithPath("contents[].creator.profileImage").type(JsonFieldType.STRING).description("농구장 추가를 요청한 사용자 프로필 이미지"),
					fieldWithPath("contents[].creator.role").type(JsonFieldType.STRING).description("농구장 추가를 요청한 사용자 권한"),
					fieldWithPath("contents[].supervisor").type(JsonFieldType.OBJECT).description("승인/거부한 관리자"),
					fieldWithPath("contents[].supervisor.id").type(JsonFieldType.STRING).description("승인/거부한 관리자 구별키"),
					fieldWithPath("contents[].supervisor.nickname").type(JsonFieldType.STRING).description("승인/거부한 관리자 닉네임"),
					fieldWithPath("lastId").type(JsonFieldType.NUMBER).description("서버에서 제공한 마지막 데이터의 구별키").optional()
				)
			));
	}

	@Test
	void accept() throws Exception {
		// given
		NewCourt acceptedCourt = NewCourt.builder()
			.id(1L)
			.name("관악구민운동장 농구장")
			.latitude(38.987654)
			.longitude(12.309472)
			.image("농구장 이미지")
			.texture(Texture.ASPHALT)
			.basketCount(2)
			.status(Status.ACCEPT)
			.createdAt(LocalDateTime.now())
			.updateAt(LocalDateTime.now())
			.build();
		User supervisor = User.builder()
			.id(3L)
			.nickname("관리자")
			.profileImage("관리자 프로필 이미지")
			.build();

		NewCourtRequest request = new NewCourtRequest(acceptedCourt.getId().toString());

		NewCourtResponse response = NewCourtResponse.toResponse(acceptedCourt, supervisor);

		given(newCourtService.acceptNewCourt(anyLong(), anyLong())).willReturn(response);

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/management/newCourt/accept")
				.header("Authorization", jwtToken)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

		// then
		resultActions.andExpect(status().isAccepted())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("id").value(acceptedCourt.getId()))
			.andExpect(jsonPath("name").value(acceptedCourt.getName()))
			.andExpect(jsonPath("latitude").value(acceptedCourt.getLatitude()))
			.andExpect(jsonPath("longitude").value(acceptedCourt.getLongitude()))
			.andExpect(jsonPath("image").value(acceptedCourt.getImage()))
			.andExpect(jsonPath("texture").value(acceptedCourt.getTexture().toString()))
			.andExpect(jsonPath("basketCount").value(acceptedCourt.getBasketCount()))
			.andExpect(jsonPath("status").value(acceptedCourt.getStatus().toString()))
			.andExpect(jsonPath("supervisor.id").value(supervisor.getId()))
			.andDo(document("management/newCourt-accept", preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("newCourtId").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 구별키")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 구별키"),
					fieldWithPath("name").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 닉네임"),
					fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 위도"),
					fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 경도"),
					fieldWithPath("image").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 이미지"),
					fieldWithPath("texture").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 바닥 재질"),
					fieldWithPath("basketCount").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 골대 수"),
					fieldWithPath("status").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 승인여부"),
					fieldWithPath("createdAt").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 정보 최초 생성시간"),
					fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 정보 최근 수정시간"),
					fieldWithPath("supervisor").type(JsonFieldType.OBJECT).description("농구장 등록을 승인한 관리자"),
					fieldWithPath("supervisor.id").type(JsonFieldType.STRING).description("관리자 구별키"),
					fieldWithPath("supervisor.nickname").type(JsonFieldType.STRING).description("관리자 닉네임"),
					fieldWithPath("supervisor.profileImage").type(JsonFieldType.STRING).description("관리자 프로필 이미지").optional()
				)
			));
	}

	@Test
	void deny() throws Exception {
		// given
		NewCourt acceptedCourt = NewCourt.builder()
			.id(1L)
			.name("관악구민운동장 농구장")
			.latitude(38.987654)
			.longitude(12.309472)
			.image("aHR0cHM6Ly9pYmIuY28vcXMwSnZXYg")
			.texture(Texture.ASPHALT)
			.basketCount(2)
			.status(Status.DENY)
			.createdAt(LocalDateTime.now())
			.updateAt(LocalDateTime.now())
			.build();
		User supervisor = User.builder()
			.id(3L)
			.nickname("관리자")
			.profileImage("관리자 프로필 이미지")
			.build();

		NewCourtRequest request = new NewCourtRequest(acceptedCourt.getId().toString());

		NewCourtResponse response = NewCourtResponse.toResponse(acceptedCourt, supervisor);

		given(newCourtService.acceptNewCourt(anyLong(), anyLong())).willReturn(response);

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/management/newCourt/accept")
				.header("Authorization", jwtToken)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

		// then
		resultActions.andExpect(status().isAccepted())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(jsonPath("id").value(acceptedCourt.getId()))
			.andExpect(jsonPath("name").value(acceptedCourt.getName()))
			.andExpect(jsonPath("latitude").value(acceptedCourt.getLatitude()))
			.andExpect(jsonPath("longitude").value(acceptedCourt.getLongitude()))
			.andExpect(jsonPath("image").value(acceptedCourt.getImage()))
			.andExpect(jsonPath("texture").value(acceptedCourt.getTexture().toString()))
			.andExpect(jsonPath("basketCount").value(acceptedCourt.getBasketCount()))
			.andExpect(jsonPath("status").value(acceptedCourt.getStatus().toString()))
			.andDo(document("management/newCourt-deny", preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("newCourtId").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 구별키")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 구별키"),
					fieldWithPath("name").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 닉네임"),
					fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 위도"),
					fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 경도"),
					fieldWithPath("image").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 이미지"),
					fieldWithPath("texture").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 바닥 재질"),
					fieldWithPath("basketCount").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 골대 수"),
					fieldWithPath("status").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 승인여부"),
					fieldWithPath("createdAt").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 정보 최초 생성시간"),
					fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 정보 최근 수정시간"),
					fieldWithPath("supervisor").type(JsonFieldType.OBJECT).description("농구장 등록을 승인한 관리자"),
					fieldWithPath("supervisor.id").type(JsonFieldType.STRING).description("관리자 구별키"),
					fieldWithPath("supervisor.nickname").type(JsonFieldType.STRING).description("관리자 닉네임"),
					fieldWithPath("supervisor.profileImage").type(JsonFieldType.STRING).description("관리자 프로필 이미지").optional()
				)
			));
	}

}