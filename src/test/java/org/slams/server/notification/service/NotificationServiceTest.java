//package org.slams.server.notification.service;
//
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.slams.server.common.api.CursorPageRequest;
//import org.slams.server.court.entity.Court;
//import org.slams.server.court.entity.Texture;
//import org.slams.server.follow.repository.FollowRepository;
//import org.slams.server.follow.service.FollowService;
//import org.slams.server.notification.dto.request.LoudspeakerNotificationRequest;
//import org.slams.server.notification.dto.response.NotificationResponse;
//import org.slams.server.court.repository.CourtRepository;
//import org.slams.server.notification.entity.Loudspeaker;
//import org.slams.server.notification.entity.Notification;
//import org.slams.server.notification.repository.LoudspeakerRepository;
//import org.slams.server.notification.repository.NotificationRepository;
//import org.slams.server.user.entity.Position;
//import org.slams.server.user.entity.Proficiency;
//import org.slams.server.user.entity.Role;
//import org.slams.server.user.entity.User;
//import org.slams.server.user.repository.UserRepository;
//
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.CoreMatchers.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//
///**
// * Created by yunyun on 2021/12/09.
// */
//
//@ExtendWith(MockitoExtension.class)
//class NotificationServiceTest {
//
//    @Mock
//    private NotificationRepository notificationRepository;
//
//    @Mock
//    private CourtRepository courtRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private FollowRepository followRepository;
//
//    @Mock
//    private LoudspeakerRepository loudspeakerRepository;
//
//    @InjectMocks
//    private FollowService followService;
//
//    @InjectMocks
//    private NotificationService notificationService;
//
//
//    Court court;
//    User receiver;
//    User creator;
//
//    @BeforeEach
//    void setup(){
//        //Given
////        court = Court.builder()
////                .id(1L)
////                .name("잠실 농구장")
////                .image("https://image.basketball.com/court1")
////                .latitude(1203.20302)
////                .longitude(12.20302)
////                .texture(Texture.CONCRETE)
////                .basketCount(1)
////                .build();
////        receiver = User.builder()
////                .id(1L)
////                .nickname("receiver")
////                .profileImage("https://image.user.com/receiver1")
////                .description("receiver 입니다.")
////                .proficiency(Proficiency.BEGINNER)
////                .positions(Arrays.asList(Position.SG, Position.PG))
////                .build();
////        creator = User.builder()
////                .id(2L)
////                .nickname("creator")
////                .profileImage("https://image.user.com/creator1")
////                .description("creator 입니다.")
////                .proficiency(Proficiency.MASTER)
////                .positions(Arrays.asList(Position.C, Position.PG))
////                .build();
//
//        User creatorEntity = User.builder()
//                .email("creator@gmail.com")
//                .nickname("creator")
//                .socialId("creator-1234")
//                .profileImage("s3에 저장된 이미지 url")
//                .description("한줄 소개")
//                .role(Role.USER)
//                .proficiency(Proficiency.BEGINNER)
//                .positions(Arrays.asList(Position.SG, Position.PG))
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//        var test = given(userRepository.save(creatorEntity)).willReturn(creatorEntity);
//
//        User receiverEntity = User.builder()
//                .email("receiver@gmail.com")
//                .nickname("receiver")
//                .socialId("receiver-1234")
//                .profileImage("receiver.s3에 저장된 이미지 url")
//                .description("receiver 한줄 소개")
//                .role(Role.USER)
//                .proficiency(Proficiency.BEGINNER)
//                .positions(Arrays.asList(Position.SG, Position.PG))
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build();
//
//        Court courtEntity = new Court(
//                "잠실 농구장",
//                1203.20302,
//                2038.2939,
//                "https://court-image",
//                1,
//                Texture.CONCRETE
//        );
//    }
//
//
//
//    @Test
//    @DisplayName("팔로우 공지를 저장할 수 있다.")
//    void saveForFollowNotification(){
//        //Given
////        User creator = User.builder()
////                .email("test@test.com")
////                .nickname("creator")
////                .socialId("creator-1234")
////                .profileImage("s3에 저장된 이미지 url")
////                .description("한줄 소개")
////                .role(Role.USER)
////                .proficiency(Proficiency.BEGINNER)
////                .positions(Arrays.asList(Position.SG, Position.PG))
////                .createdAt(LocalDateTime.now())
////                .updatedAt(LocalDateTime.now())
////                .build();
////
////        User receiver = User.builder()
////                .email("test@test.com")
////                .nickname("creator")
////                .socialId("creator-1234")
////                .profileImage("s3에 저장된 이미지 url")
////                .description("한줄 소개")
////                .role(Role.USER)
////                .proficiency(Proficiency.BEGINNER)
////                .positions(Arrays.asList(Position.SG, Position.PG))
////                .createdAt(LocalDateTime.now())
////                .updatedAt(LocalDateTime.now())
////                .build();
////
////        //When, Then
////        Follow followEntity = Follow.of(creator, receiver);
////        Notification followNotification = Notification.createFollow(1L, followEntity);
////        Mockito.lenient().when(notificationRepository.save(followNotification)).thenReturn(followNotification);
//    }
//
//    @Test
//    @DisplayName("확성기 공지를 저장할 수 있다.")
//    void saveForLoudspeakerNotification(){
//        //When, Then
//        LoudspeakerNotificationRequest loudspeakerNotificationRequest = new LoudspeakerNotificationRequest(
//                court.getId(),
//                LocalDateTime.now(),
//                LocalDateTime.now().plusHours(2),
//                1L
//        );
//
//        NotificationResponse notificationResponse = notificationService
//                .saveForLoudSpeakerNotification(
//                        loudspeakerNotificationRequest,
//                        receiver.getId(),
//                        creator.getId()
//                );
//
//        Mockito.lenient()
//                .when(notificationResponse)
//                .thenReturn(
//                        NotificationResponse.createForLoudspeakerNotification(
//                                notificationResponse.getId(),
//                                notificationResponse.getType(),
//                                notificationResponse.getLoudspeaker(),
//                                notificationResponse.getIsRead(),
//                                notificationResponse.getIsClicked(),
//                                notificationResponse.getCreatedAt(),
//                                notificationResponse.getUpdatedAt()
//                        )
//                );
//
//    }
//
//
//    @Test
//    @DisplayName("사용자 구별키를 이용하여, 공지의 최초 정보부터 특정 개수의 정보를 추출할 수 있다.")
//    void findAllByUserIdIsFirstTrue(){
//        //Given
////        CursorPageRequest cursorRequest = new CursorPageRequest(5, 2L, true);
////        Court courtEntity = new Court(
////                "잠실 농구장",
////                1203.20302,
////                2038.2939,
////                "https://court-image",
////                1,
////                Texture.CONCRETE
////        );
////        User userA = User.builder()
////                .email("userA@gmail.com")
////                .nickname("userA")
////                .socialId("userA-1234")
////                .profileImage("userA s3에 저장된 이미지 url")
////                .description("userA 한줄 소개")
////                .role(Role.USER)
////                .proficiency(Proficiency.MASTER)
////                .positions(Arrays.asList(Position.SG, Position.PG))
////                .createdAt(LocalDateTime.now())
////                .updatedAt(LocalDateTime.now())
////                .build();
////        User userB = User.builder()
////                .email("userB@gmail.com")
////                .nickname("userB")
////                .socialId("userB-1234")
////                .profileImage("userB s3에 저장된 이미지 url")
////                .description("userB 한줄 소개")
////                .role(Role.USER)
////                .proficiency(Proficiency.BEGINNER)
////                .positions(Arrays.asList(Position.SG))
////                .createdAt(LocalDateTime.now())
////                .updatedAt(LocalDateTime.now())
////                .build();
////
////        Long receiverId = 1L;
//
//        /** 농구장 확성기 메시지 **/
////        Loudspeaker loudspeakerEntityA = Loudspeaker.of(userA, courtEntity, LocalDateTime.now(), LocalDateTime.now());
////        notificationRepository.save(
////                Notification.createLoudspeaker(receiverId, loudspeakerEntityA)
////        );
//
//        /** 농구장 확성기 메시지 **/
////        Loudspeaker loudspeakerEntityB = Loudspeaker.of(userB, courtEntity, LocalDateTime.now(), LocalDateTime.now());
////        notificationRepository.save(
////                Notification.createLoudspeaker(receiverId, loudspeakerEntityB)
////        );
//
//        /** 팔로우 메시지 **/
////        Follow followEntity = Follow.of(userA, userB);
////        notificationRepository.save(
////                Notification.createFollow(receiverId, followEntity)
////        );
//
//        //When, Then
////        notificationService.findAllByUserId()
//
//
//
//    }
//
//    @Test
//    @DisplayName("사용자 구별키를 이용하여, 마지막으로 읽은 공지부터 특정 개수의 정보를 추출할 수 있다.")
//    void findAllByUserIdIsFirstFalse(){
//        //Given
//        CursorPageRequest cursorRequest = new CursorPageRequest(5, 2L, false);
//
//        //When
//
//        //Then
//
//    }
//
//
//    @Test
//    @DisplayName("읽음 표기 기능을 할 수 있다.")
//    void updateIsClickedStatus(){
//        //When
//
//        //Given
//
//        //Then
//
//    }
//
//    @Test
//    @DisplayName("공지의 마지막 인데스 값을 알 수 있다.")
//    void findLastId(){
//        //When
//
//        //Given
//
//        //Then
//
//    }
//
//
//
////    @Test
////    @Order(1)
////    @DisplayName("사용자 구별키를 이용하여, 공지의 최초 정보부터 특정 개수의 정보를 추출할 수 있다.")
////    void findAllByUserIdIsFirstTrue(){
////        //Given
////        CursorPageRequest cursorRequest = new CursorPageRequest();
////        cursorRequest.setSize(5);
////        cursorRequest.setIsFirst(true);
////        cursorRequest.setLastId(0L);
////
////
////        /** following 알림 메시지 **/
////        FollowNotification followNotification = FollowNotification.of(
////                receiver,
////                1L,
////                NotificationType.FOLLOWING
////        );
////        notificationIndexRepository.save(NotificationIndex.of(followNotification.getId(), 1L));
////        followNotificationRepository.save(
////                followNotification
////        );
////
////        /** 농구장 확성기 알림 메시지 **/
////        LoudSpeakerNotification loudSpeakerNotification = LoudSpeakerNotification.of(
////                court,
////                12,
////                1L,
////                NotificationType.LOUDSPEAKER
////        );
////
////        notificationIndexRepository.save(NotificationIndex.of(loudSpeakerNotification.getId(), 1L));
////        loudSpeakerNotificationRepository.save(
////                loudSpeakerNotification
////        );
////
////
////        /** 농구장 확성기 알림 메시지 **/
////        LoudSpeakerNotification loudSpeakerNotification2 = LoudSpeakerNotification.of(
////                court,
////                15,
////                user.getId(),
////                NotificationType.LOUDSPEAKER
////        );
////        notificationIndexRepository.save(NotificationIndex.of(loudSpeakerNotification2.getId(), user.getId()));
////        loudSpeakerNotificationRepository.save(
////                loudSpeakerNotification2
////        );
////
////        //When
////        List<NotificationResponse> notificationResponseList = notificationService.findAllByUserId(
////                user.getId(),
////                cursorRequest
////        );
////
////        //Then
////        assertThat(notificationResponseList.size(), is(3));
////        assertThat(notificationResponseList.get(0).getType(), is(NotificationType.FOLLOWING));
////        assertThat((notificationResponseList.get(0).getFollowerInfo().getUserNickname()), is("receiver"));
////        assertThat(notificationResponseList.get(1).getType(), is(NotificationType.LOUDSPEAKER));
////        assertThat(notificationResponseList.get(1).getLoudspeakerInfo().getCourtInfo().getName(), containsString("잠실"));
////    }
////
////    @Test
////    @Order(2)
////    @DisplayName("사용자 구별키를 이용하여, 마지막으로 읽은 공지부터 특정 개수의 정보를 추출할 수 있다.")
////    void findAllByUserIdIsFirstFalse(){
////        //Given
////        CursorPageRequest cursorRequest = new CursorPageRequest();
////        cursorRequest.setSize(5);
////        cursorRequest.setIsFirst(false);
////        cursorRequest.setLastId(2L);
////
////
////        //When
////        List<NotificationResponse> notificationResponseList = notificationService.findAllByUserId(
////                user.getId(),
////                cursorRequest
////        );
////
////        //Then
////        assertThat(notificationResponseList.size(), is(2));
////        assertThat(notificationResponseList.get(0).getType(), is(NotificationType.LOUDSPEAKER));
////        assertThat((notificationResponseList.get(0).getLoudspeakerInfo().getCourtInfo().getName()), containsString("잠실"));
////        assertThat(notificationResponseList.get(1).getType(), is(NotificationType.LOUDSPEAKER));
////        assertThat(notificationResponseList.get(1).getLoudspeakerInfo().getCourtInfo().getName(), containsString("잠실"));
////
////    }
////
////    @Test
////    @Order(3)
////    @DisplayName("팔로우 공지를 저장할 수 있다.")
////    void saveForFollowNotification(){
////        //Given
////        Long userId = user.getId();
////        FollowNotificationRequest followNotificationRequest = new FollowNotificationRequest(receiver.getId());
////
////        //When
////        notificationService.saveForFollowNotification(
////                followNotificationRequest,
////                userId
////        );
////
////        //Then
////        List<NotificationIndex> notificationIndexList = notificationIndexRepository.findAll();
////        List<FollowNotification> followNotificationList = followNotificationRepository.findAll();
////        assertThat(notificationIndexList.size(), is(4));
////        assertThat(followNotificationList.size(), is(2));
////        assertThat(followNotificationList.get(1).getReceiver().getNickname(), is("receiver"));
////    }
////
////    @Test
////    @Order(5)
////    @DisplayName("확성기 공지를 저장할 수 있다.")
////    void saveForLoudspeakerNotification(){
////        //Given
////        Long userId = user.getId();
////
////        LoudspeakerNotificationRequest loudspeakerNotificationRequest = new LoudspeakerNotificationRequest(
////                court.getId(),
////                13,
////                1L
////                );
////
////        //When
////        notificationService.saveForLoudSpeakerNotification(loudspeakerNotificationRequest, userId);
////
////        //Then
////        List<NotificationIndex> notificationIndexList = notificationIndexRepository.findAll();
////        List<LoudSpeakerNotification> loudSpeakerNotificationList = loudSpeakerNotificationRepository.findAll();
////        assertThat(notificationIndexList.size(), is(5));
////        assertThat(loudSpeakerNotificationList.size(), is(3));
////    }
////
////    @Test
////    @Order(6)
////    @DisplayName("읽음 표기 기능을 할 수 있다.")
////    void updateIsClickedStatus(){
////        //Given
////        Long userId = user.getId();
////
////        CursorPageRequest cursorRequest = new CursorPageRequest();
////        cursorRequest.setSize(5);
////        cursorRequest.setIsFirst(true);
////        cursorRequest.setLastId(0L);
////
////
////        //When
////        notificationService.updateIsClickedStatus(
////                new UpdateIsClickedStatusRequest(true),
////                userId
////        );
////
////        assertThat(notificationIndexRepository.findAll().size(), is(5));
////        List<NotificationResponse> notificationResponseList = notificationService.findAllByUserId(userId, cursorRequest);
////        assertThat(notificationResponseList.get(0).isClicked(), is(true));
//////        assertThat(notificationResponseList.get(1).isRead(), is(true));
//////        assertThat(notificationResponseList.get(2).isRead(), is(true));
////    }
////
////    @Test
////    @Order(7)
////    @DisplayName("공지의 마지막 인데스 값을 알 수 있다.")
////    void findLastId(){
////        //Given
////        Long userId = user.getId();
////        CursorPageRequest cursorRequest = new CursorPageRequest();
////        cursorRequest.setSize(5);
////        cursorRequest.setIsFirst(true);
////        cursorRequest.setLastId(0L);
////
////
////        //When
////        Long lastId = notificationService.findNotificationLastId(userId, cursorRequest);
////
////        //Then
////        List<NotificationIndex> notificationIndexList = notificationIndexRepository.findAll();
////        assertThat(lastId, is(notificationIndexList.get(notificationIndexList.size()-1).getId()));
////    }
//
//}