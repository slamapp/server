package org.slams.server.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.slams.server.court.entity.Court;
import org.slams.server.court.entity.Texture;
import org.slams.server.court.repository.CourtRepository;
import org.slams.server.court.service.CourtService;
import org.slams.server.reservation.dto.request.ReservationInsertRequestDto;
import org.slams.server.reservation.dto.request.ReservationUpdateRequestDto;
import org.slams.server.reservation.dto.response.ReservationDeleteResponseDto;
import org.slams.server.reservation.dto.response.ReservationInsertResponseDto;
import org.slams.server.reservation.dto.response.ReservationUpcomingResponseDto;
import org.slams.server.reservation.dto.response.ReservationUpdateResponseDto;
import org.slams.server.reservation.entity.Reservation;
import org.slams.server.reservation.repository.ReservationRepository;
import org.slams.server.reservation.service.ReservationService;
import org.slams.server.user.entity.Position;
import org.slams.server.user.entity.Proficiency;
import org.slams.server.user.entity.Role;
import org.slams.server.user.entity.User;
import org.slams.server.user.repository.UserRepository;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Slf4j
public class ReservationControllerTest {


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReservationService reservationService;

    private User user;
    private Court court;
    private Reservation reservation;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourtService courtService;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    LocalDateTime now = LocalDateTime.now();

    // JWT ?????? ??????
    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception{


        // JWT ?????? ??????
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
        jwtToken = "Bearer "+token;


        // User ??????
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
        user.setUpdatedAt(now);



        // Court ??????
        court=Court.builder()
                .name("????????????????????? ?????????")
                .latitude(38.987654)
                .longitude(12.309472)
                .image("aHR0cHM6Ly9pYmIuY28vcXMwSnZXYg")
                .texture(Texture.ASPHALT)
                .basketCount(2)
                .build();

        court.setCreatedAt(now);
        court.setUpdatedAt(now);

        court=Court.builder()
                .id(127L)
                .name("????????? ?????????")
                .latitude(45.987654)
                .longitude(13.309472)
                .image("aHR0cHM6Ly9pYmIuY28vcXMwSnZXYg")
                .texture(Texture.ASPHALT)
                .basketCount(4)
                .build();

        court.setCreatedAt(now);
        court.setUpdatedAt(now);

        reservation=Reservation.builder()
                .id(1L)
                .court(court)
                .user(user)
                .hasBall(false)
                .startTime(now)
                .endTime(now)
                .build();

        reservation.setCreatedAt(now);
        reservation.setUpdatedAt(now);

    }


    // ????????? ????????? ?????? ?????????
    @Test
    @DisplayName("[POST] '/api/v1/reservations'")
    void testInsertCall() throws Exception {
        // GIVEN

        LocalDateTime start=now.plusDays(1);
        LocalDateTime end=now.plusDays(1);

        ReservationInsertRequestDto givenRequest = ReservationInsertRequestDto.builder()
                .courtId(1L)
                .startTime(start)
                .endTime(end)
                .hasBall(false)
                .build();



        ReservationInsertResponseDto stubResponse = new ReservationInsertResponseDto(reservation);
        given(reservationService.insert(any(), any())).willReturn(stubResponse);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/reservations")
                .header("Authorization",jwtToken)
                .contentType(MediaType.APPLICATION_JSON) // TODO: ?????? ???????????? multipart/form-data
                .content(objectMapper.writeValueAsString(givenRequest));

        // WHEN // THEN
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("reservation-save",
                        requestFields(
                                fieldWithPath("courtId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("startTime").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("endTime").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("hasBall").type(JsonFieldType.BOOLEAN).description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("reservationId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("courtId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("startTime").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("endTime").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("hasBall").type(JsonFieldType.BOOLEAN).description("????????? ??????"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("?????? ????????????")
                        )
                ));
    }


    //  ????????????
    @Test
    @DisplayName("[PATCH] '/api/v1/reservations/{reservationId}'")
    void testUpdateCall() throws Exception {
        // ????????? ?????? ????????? ????????????
        // ???????????? ??????
        // GIVEN
        LocalDateTime start=now.plusDays(5);
        LocalDateTime end=now.plusDays(5);
        ReservationUpdateRequestDto givenRequest = ReservationUpdateRequestDto.builder()
                .reservationId(1L)
                .startTime(start)
                .endTime(end)
                .hasBall(false)
                .build();

        reservation.update(givenRequest);

        ReservationUpdateResponseDto stubResponse = new ReservationUpdateResponseDto(reservation);
        given(reservationService.update(any(), any(), any())).willReturn(stubResponse);


        RequestBuilder request = MockMvcRequestBuilders.patch("/api/v1/reservations/"+1L)
                .header("Authorization",jwtToken)
                .contentType(MediaType.APPLICATION_JSON) // TODO: ?????? ???????????? multipart/formdata
                .content(objectMapper.writeValueAsString(givenRequest));

        // WHEN // THEN
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("reservation-update",
                        requestFields(
                                fieldWithPath("reservationId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("startTime").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("endTime").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("hasBall").type(JsonFieldType.BOOLEAN).description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("reservationId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("courtId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("startTime").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("endTime").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("hasBall").type(JsonFieldType.BOOLEAN).description("????????? ??????"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("?????? ????????????")
                        )
                ));
    }

    // ????????????
    @Test
    @DisplayName("[DELETE] '/api/v1/reservations/{reservationId}'")
    void testDelete() throws Exception {

        ReservationDeleteResponseDto response = new ReservationDeleteResponseDto(reservation);
        given(reservationService.delete(any(),anyLong())).willReturn(response);

        mockMvc.perform(delete("/api/v1/reservations/{reservationId}", reservation.getId())
                        .header("Authorization",jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andDo(print())
                .andDo(
                        document("reservation/delete", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                                pathParameters(
                                        parameterWithName("reservationId").description("?????? ?????? reservation ?????????")
                                ),
                                responseFields(
                                        fieldWithPath("reservationId").description("????????? reservation ?????????")
                                )
                        )
                );
    }


    // ?????? 1?????? 3??? ????????????
    // 4,5,6??? ?????? ??????
    @Test
    @DisplayName("?????? ????????? ?????? [POST] '/api/v1/reservations'")
    @Disabled
    void InsertReservation() throws Exception {
        // GIVEN

        // User ??????
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
        user.setUpdatedAt(now);
        userRepository.save(user);

        LocalDateTime start=now.plusHours(1);
        LocalDateTime end=now.plusMonths(1);

        Court court1 = courtRepository.getById(3L);
        Court court2 = courtRepository.getById(4L);
        Court court3 = courtRepository.getById(5L);

        Reservation reservation1=Reservation.builder()
                .court(court1)
                .startTime(start)
                .endTime(now.plusHours(1))
                .hasBall(false)
                .user(user)
                .build();

        reservationRepository.save(reservation1);

        Reservation reservation2=Reservation.builder()
                .court(court1)
                .startTime(start)
                .endTime(now.plusHours(1))
                .hasBall(false)
                .user(user)
                .build();


        reservationRepository.save(reservation2);

        Reservation reservation3=Reservation.builder()
                .court(court1)
                .startTime(start)
                .endTime(now.plusHours(1))
                .hasBall(false)
                .user(user)
                .build();

        reservationRepository.save(reservation3);


        Reservation reservation4=Reservation.builder()
                .court(court2)
                .startTime(start)
                .endTime(now.plusHours(2))
                .hasBall(false)
                .user(user)
                .build();

        reservationRepository.save(reservation4);

        Reservation reservation5=Reservation.builder()
                .court(court2)
                .startTime(start)
                .endTime(now.plusHours(2))
                .hasBall(false)
                .user(user)
                .build();


        reservationRepository.save(reservation5);


        Reservation reservation6=Reservation.builder()
                .court(court3)
                .startTime(start)
                .endTime(now.plusHours(3))
                .hasBall(false)
                .user(user)
                .build();

        reservationRepository.save(reservation6);

    }


    // ????????? ?????? ?????? ??????
    // /api/v1/reservations/upcoming
    @Test
    @Order(2)
    @DisplayName("[GET] '/api/v1/reservations/upcoming")
    @Transactional
    void testSelectCall() throws Exception {
        LocalDateTime start=now.plusHours(1);
        LocalDateTime end=now.plusMonths(1);
        // GIVEN
        List<ReservationUpcomingResponseDto> stubResponses = new ArrayList<>();
        ReservationUpcomingResponseDto reservationUpcomingResponseDto=new ReservationUpcomingResponseDto(
                reservation,3L);

        ReservationUpcomingResponseDto reservationUpcomingResponseDto2=new ReservationUpcomingResponseDto(
                reservation,3L);

        stubResponses.add(reservationUpcomingResponseDto);


        given(reservationService.findUpcoming(any())).willReturn(stubResponses);



        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/reservations/upcoming")
                .header("Authorization",jwtToken)
                .contentType(MediaType.APPLICATION_JSON); // TODO: ?????? ???????????? multipart/form-data

        // WHEN // THEN
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("reservationsByUserByNow-select",
                        responseFields(
                                fieldWithPath("reservations").type(JsonFieldType.ARRAY).description("data"),
                                fieldWithPath("reservations.[].reservationId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                fieldWithPath("reservations.[].courtId").type(JsonFieldType.NUMBER).description("????????? ?????? ?????????"),
                                fieldWithPath("reservations.[].courtName").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                                fieldWithPath("reservations.[].latitude").type(JsonFieldType.NUMBER).description("????????? ?????? ??????"),
                                fieldWithPath("reservations.[].longitude").type(JsonFieldType.NUMBER).description("????????? ?????? ??????"),
                                fieldWithPath("reservations.[].basketCount").type(JsonFieldType.NUMBER).description("????????? ?????? ????????????"),
                                fieldWithPath("reservations.[].numberOfReservations").type(JsonFieldType.NUMBER).description("????????? ?????? ???"),
                                fieldWithPath("reservations.[].startTime").type(JsonFieldType.STRING).description("????????? ????????? ????????????"),
                                fieldWithPath("reservations.[].endTime").type(JsonFieldType.STRING).description("????????? ????????? ????????????"),
                                fieldWithPath("reservations.[].createdAt").type(JsonFieldType.STRING).description("?????? ????????????"),
                                fieldWithPath("reservations.[].updatedAt").type(JsonFieldType.STRING).description("?????? ????????????")

                        )
                ));
    }







}
