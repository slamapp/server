package org.slams.server.favorite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slams.server.court.entity.Court;
import org.slams.server.favorite.dto.request.FavoriteInsertRequest;
import org.slams.server.favorite.dto.response.FavoriteInsertResponse;
import org.slams.server.favorite.dto.response.FavoriteLookUpResponse;
import org.slams.server.favorite.entity.Favorite;
import org.slams.server.favorite.service.FavoriteService;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
class FavoriteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private FavoriteService favoriteService;

	private String jwtToken;

	private Court court;
	private User user;
	private Favorite favorite;

	@BeforeEach
	void setUp() throws IOException {
		// ???????????? ??????
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();

		// ???????????? ?????? ?????? ??????
		ConfigurableEnvironment env = ctx.getEnvironment();

		// ???????????? ?????? ?????? ??????
		MutablePropertySources prop = env.getPropertySources();

		// ???????????? ?????? ????????? ???????????? ?????? ??????
		prop.addLast(new ResourcePropertySource("classpath:test.properties"));

		// ???????????? ?????? ??????
		String token = env.getProperty("token");
		jwtToken = "Bearer " + token;

		court = Court.builder()
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.id(1L)
			.name("?????????????????? ?????????")
			.latitude(123.456)
			.longitude(89.567)
			.build();
		user = User.builder()
			.id(2L)
			.nickname("?????????")
			.build();
		favorite = Favorite.builder()
			.id(1L)
			.court(court)
			.user(user)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}

	@Test
	void insert() throws Exception {
		// given
		FavoriteInsertRequest request = FavoriteInsertRequest.builder()
			.courtId(court.getId().toString()).build();

		FavoriteInsertResponse response = FavoriteInsertResponse.toResponse(favorite);

		given(favoriteService.insert(any(), anyLong())).willReturn(response);

		// when
		ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/favorites")
				.header("Authorization", jwtToken)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

		// then
		resultActions.andExpect(status().isCreated())
			.andDo(document("favorites/favorite-insert", preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("courtId").type(JsonFieldType.STRING).description("????????? ?????????")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.STRING).description("???????????? ?????????"),
					fieldWithPath("createdAt").type(JsonFieldType.STRING).description("???????????? ?????? ?????? ????????????"),
					fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("???????????? ?????? ?????? ????????????"),
					fieldWithPath("court").type(JsonFieldType.OBJECT).description("???????????? ?????????"),
					fieldWithPath("court.id").type(JsonFieldType.STRING).description("????????? ?????????"),
					fieldWithPath("court.name").type(JsonFieldType.STRING).description("????????? ??????"),
					fieldWithPath("court.latitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
					fieldWithPath("court.longitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
					fieldWithPath("court.createdAt").type(JsonFieldType.STRING).description("????????? ?????? ?????? ????????????"),
					fieldWithPath("court.updatedAt").type(JsonFieldType.STRING).description("????????? ?????? ?????? ????????????")
				)
			));
	}

	@Test
	void getAll() throws Exception {
		// given
		FavoriteLookUpResponse response = FavoriteLookUpResponse.toResponse(favorite);

		given(favoriteService.getAll(anyLong())).willReturn(List.of(response));

		// when
		ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/favorites")
				.header("Authorization", jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

		// then
		resultActions.andExpect(status().isOk())
			.andDo(document("favorites/favorite-getAll", preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("[].id").type(JsonFieldType.STRING).description("???????????? ?????????"),
					fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("???????????? ?????? ?????? ????????????"),
					fieldWithPath("[].updatedAt").type(JsonFieldType.STRING).description("???????????? ?????? ?????? ????????????"),
					fieldWithPath("[].court").type(JsonFieldType.OBJECT).description("???????????? ?????????"),
					fieldWithPath("[].court.id").type(JsonFieldType.STRING).description("????????? ?????????"),
					fieldWithPath("[].court.name").type(JsonFieldType.STRING).description("????????? ??????"),
					fieldWithPath("[].court.latitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
					fieldWithPath("[].court.longitude").type(JsonFieldType.NUMBER).description("????????? ??????"),
					fieldWithPath("[].court.createdAt").type(JsonFieldType.STRING).description("????????? ?????? ?????? ????????????"),
					fieldWithPath("[].court.updatedAt").type(JsonFieldType.STRING).description("????????? ?????? ?????? ????????????")
				)
			));
	}

	@Test
	void delete() throws Exception {
		// given
		willDoNothing().given(favoriteService).delete(anyLong());

		// when
		ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.delete("/api/v1/favorites/{favoriteId}", favorite.getId())
				.header("Authorization", jwtToken)
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

		// then
		resultActions.andExpect(status().isNoContent())
			.andDo(document("favorites/favorite-delete", preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())));
	}

}