package org.slams.server.court.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.slams.server.court.dto.request.NewCourtInsertRequest;
import org.slams.server.court.dto.response.CourtDetailResponse;
import org.slams.server.court.dto.response.NewCourtInsertResponse;
import org.slams.server.court.dto.response.CourtReservationResponseDto;
import org.slams.server.court.entity.Court;
import org.slams.server.court.entity.NewCourt;
import org.slams.server.court.entity.Status;
import org.slams.server.court.entity.Texture;
import org.slams.server.court.service.CourtService;
import org.slams.server.court.service.NewCourtService;
import org.slams.server.reservation.entity.Reservation;
import org.slams.server.user.entity.Position;
import org.slams.server.user.entity.Proficiency;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Slf4j
public class CourtControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NewCourtService newCourtService;

    @MockBean
    private CourtService courtService;

    private User user;
    private Court court;
    private NewCourt newCourt;
    private Reservation reservation;




    // JWT 추가 코드
    private String jwtToken;



    @BeforeEach
    void setUp() throws Exception{

        // JWT 추가 코드
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
        jwtToken = "Bearer "+token;

        // user ID + role + 토큰 발행일자 + 만료일자 ==> token
        LocalDateTime now = LocalDateTime.now();
        // User 생성
        user = User.builder()
                .nickname("test")
                .email("sds1zzang@naver.com")
                .id(1L)
                .socialId("1L")
                .description("my name is sds")
                .profileImage("desktop Image")
                .role(Role.USER)
                .proficiency(Proficiency.INTERMEDIATE.BEGINNER)
                .positions(Arrays.asList(Position.PF))
                .build();

        user.setCreatedAt(now);
        user.setUpdateAt(now);



        // Court 생성
        newCourt=NewCourt.builder()
                .id(1L)
                .name("관악구민운동장 농구장")
                .latitude(38.987654)
                .longitude(12.309472)
                .image("aHR0cHM6Ly9pYmIuY28vcXMwSnZXYg")
                .texture(Texture.ASPHALT)
                .basketCount(2)
                .status(Status.READY)
                .build();

        newCourt.setCreatedAt(now);
        newCourt.setUpdateAt(now);

        court=Court.builder()
                .id(1L)
                .name("관악구민운동장 농구장")
                .latitude(38.987654)
                .longitude(12.309472)
                .image("aHR0cHM6Ly9pYmIuY28vcXMwSnZXYg")
                .texture(Texture.ASPHALT)
                .basketCount(2)
                .build();

        court.setCreatedAt(now);
        court.setUpdateAt(now);

        reservation=Reservation.builder()
                .id(1L)
                .court(court)
                .hasBall(false)
                .startTime(now.plusHours(1))
                .endTime(now.plusHours(5))
                .user(user)
                .build();

        reservation.setCreatedAt(now);
        reservation.setUpdateAt(now);


    }


    @Test
    @DisplayName("[POST] /api/v1/courts/new")
    void insert() throws Exception {
        // given
        NewCourtInsertRequest givenRequest = NewCourtInsertRequest.builder()
                .name("관악구민운동장 농구장")
                .latitude(38.987654)
                .longitude(12.309472)
                .image("s3에 저장된 새 농구장 이미지 url")
                .texture(Texture.ASPHALT)
                .basketCount(2)
                .build();

        NewCourtInsertResponse stubResponse = NewCourtInsertResponse.toResponse(newCourt);
        given(newCourtService.insert(any(), any())).willReturn(stubResponse);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/courts/new")
                .header("Authorization", jwtToken)
                .content(objectMapper.writeValueAsString(givenRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print());

        // then
        resultActions.andExpect(status().isCreated())
            .andDo(document("courts/newCourt-insertNewCourt", preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("새 농구장 이름"),
                    fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("위도"),
                    fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("경도"),
                    fieldWithPath("image").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 이미지"),
                    fieldWithPath("texture").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 재질"),
                    fieldWithPath("basketCount").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 골대 갯수")
                ),
                responseFields(
                    fieldWithPath("newCourt").type(JsonFieldType.OBJECT).description("사용자가 추가한 농구장"),
                    fieldWithPath("newCourt.id").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 구별키"),
                    fieldWithPath("newCourt.name").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 이름"),
                    fieldWithPath("newCourt.latitude").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 위도"),
                    fieldWithPath("newCourt.longitude").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 경도"),
                    fieldWithPath("newCourt.image").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 이미지"),
                    fieldWithPath("newCourt.texture").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 재질"),
                    fieldWithPath("newCourt.basketCount").type(JsonFieldType.NUMBER).description("사용자가 추가한 농구장 골대 갯수"),
                    fieldWithPath("newCourt.status").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 승인여부"),
                    fieldWithPath("newCourt.createdAt").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 정보 최초 생성일자"),
                    fieldWithPath("newCourt.updatedAt").type(JsonFieldType.STRING).description("사용자가 추가한 농구장 정보 최근 수정일자")
                )
            ));
    }

    @Test
    @DisplayName("[GET] /api/v1/courts/{courtId}/detail")
    void getDetail() throws Exception {
        // given
		CourtDetailResponse response = CourtDetailResponse.toResponse(court, 1L);

		given(courtService.findDetail(anyLong(), any(), any())).willReturn(response);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/courts/{courtId}/detail", court.getId())
				.header("Authorization", jwtToken)
                .param("date", "2022-01-01")
                .param("time", "dawn")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print());

        // then

        resultActions.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andDo(document("courts/court-getDetail", preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("court").type(JsonFieldType.OBJECT).description("농구장"),
                    fieldWithPath("court.id").type(JsonFieldType.STRING).description("농구장 구별키"),
                    fieldWithPath("court.name").type(JsonFieldType.STRING).description("농구장 이름"),
                    fieldWithPath("court.latitude").type(JsonFieldType.NUMBER).description("농구장 위도"),
                    fieldWithPath("court.longitude").type(JsonFieldType.NUMBER).description("농구장 경도"),
                    fieldWithPath("court.image").type(JsonFieldType.STRING).description("농구장 이미지"),
                    fieldWithPath("court.basketCount").type(JsonFieldType.NUMBER).description("농구장 골대 갯수"),
                    fieldWithPath("court.texture").type(JsonFieldType.STRING).description("농구장 바닥 재질"),
                    fieldWithPath("court.createdAt").type(JsonFieldType.STRING).description("농구장 정보 최초 생성시간"),
                    fieldWithPath("court.updatedAt").type(JsonFieldType.STRING).description("농구장 정보 최근 수정시간"),
                    fieldWithPath("reservationMaxCount").type(JsonFieldType.NUMBER).description("농구장 예약 최대 갯수")
				)
			));
    }


//    @Test
//    @DisplayName("[GET] '/api/v1/courts/detail/{courtId}/{date}/{time}")
//    void testDetailCourt() throws Exception {
//
//        // 사용자가 예약한 코트에 대한 정보 알기
//
//        // GIVEN
//        LocalDateTime now = LocalDateTime.now();
//        court=Court.builder()
//                .id(1L)
//                .name("관악구민운동장 농구장")
//                .latitude(38.987654)
//                .longitude(12.309472)
//                .image("aHR0cHM6Ly9pYmIuY28vcXMwSnZXYg")
//                .texture(Texture.ASPHALT)
//                .basketCount(2)
//                .build();
//
//        court.setCreatedAt(now);
//        court.setUpdateAt(now);
//
//
//        CourtDetailResponse courtDetailResponse =new CourtDetailResponse(court, 3L);
//
//        given(courtService.findDetail(any(),any(),any())).willReturn(courtDetailResponse);
//        String date="2019-04-20";
//        String time="dawn";
//
//
//        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/courts/detail/1/"+date+"/"+time)
//                .header("Authorization",jwtToken)
//                .contentType(MediaType.APPLICATION_JSON); // TODO: 사진 들어오면 multipart/form-data
//
//        // WHEN // THEN
//        mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(document("AllCourt-select",
//                        responseFields(
//                                fieldWithPath("courtName").type(JsonFieldType.STRING).description("코트 이름"),
//                                fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("위도"),
//                                fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("경도"),
//                                fieldWithPath("image").type(JsonFieldType.STRING).description("코트 이미지"),
//                                fieldWithPath("texture").type(JsonFieldType.STRING).description("코트 재질"),
//                                fieldWithPath("basketCount").type(JsonFieldType.NUMBER).description("골대 갯수"),
//                                fieldWithPath("courtReservation").type(JsonFieldType.NUMBER).description("농구장 코트 예약한 수"),
//                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("코트 생성일자"),
//                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("코트 수정일자")
//                        )
//                ));
//    }

}
