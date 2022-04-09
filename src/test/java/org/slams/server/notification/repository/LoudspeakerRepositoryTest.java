package org.slams.server.notification.repository;

import org.junit.jupiter.api.Test;
import org.slams.server.court.entity.Court;
import org.slams.server.court.entity.Texture;
import org.slams.server.court.repository.CourtRepository;
import org.slams.server.follow.entity.Follow;
import org.slams.server.follow.repository.FollowRepository;
import org.slams.server.notification.entity.Loudspeaker;
import org.slams.server.notification.entity.Notification;
import org.slams.server.user.entity.Position;
import org.slams.server.user.entity.Proficiency;
import org.slams.server.user.entity.Role;
import org.slams.server.user.entity.User;
import org.slams.server.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ExtendWith(SpringExtension.class)
class LoudspeakerRepositoryTest {

    @Autowired
    private LoudspeakerRepository loudspeakerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void saveLoudspeaker(){
        //Given
        User user = User.builder()
                .email("jelly@gmail.com")
                .nickname("젤리")
                .socialId("1234")
                .profileImage("s3에 저장된 이미지 url")
                .description("한줄 소개")
                .role(Role.USER)
                .proficiency(Proficiency.BEGINNER)
                .positions(Arrays.asList(Position.SG, Position.PG))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        Court court = new Court(
                "잠실 농구장",
                1203.20302,
                2038.2939,
                "https://court-image",
                1,
                Texture.CONCRETE
        );
        courtRepository.save(court);

        //When
        loudspeakerRepository.save(
                Loudspeaker
                        .of(user, court, LocalDateTime.now(), LocalDateTime.now())
        );
    }

    @Test
    void saveNotificationFollow(){
        //Given
        LocalDateTime nowDate = LocalDateTime.now();
        String creatorEmail = "creator@gmail.com";
        String receiverSocialId = "receiver-1234";

        User creator = User.builder()
                .email(creatorEmail)
                .nickname("creator")
                .socialId("creator-1234")
                .profileImage("s3에 저장된 이미지 url")
                .description("한줄 소개")
                .role(Role.USER)
                .proficiency(Proficiency.BEGINNER)
                .positions(Arrays.asList(Position.SG, Position.PG))
                .createdAt(nowDate)
                .updatedAt(nowDate)
                .build();
        User creatorSaved = userRepository.saveAndFlush(creator);

        User receiver = User.builder()
                .email("receiver@gmail.com")
                .nickname("receiver")
                .socialId(receiverSocialId)
                .profileImage("s3에 저장된 이미지 url")
                .description("한줄 소개")
                .role(Role.USER)
                .proficiency(Proficiency.BEGINNER)
                .positions(Arrays.asList(Position.SG, Position.PG))
                .createdAt(nowDate)
                .updatedAt(nowDate)
                .build();
        userRepository.save(receiver);

        Follow followEntity = Follow.of(creator, receiver);
        followRepository.saveAndFlush(followEntity);

        //When
        Notification notificationEntity = Notification.createFollow(creatorSaved.getId(), followEntity);
        notificationRepository.save(notificationEntity);

        //Then
        assertThat(notificationEntity.getFollow().getFollower().getEmail(), is(creatorEmail));
        assertThat(notificationEntity.getFollow().getFollowing().getSocialId(), is(receiverSocialId));

    }

    @Test
    void saveNotificationLoudspeaker(){
        //Given
        String creatorEmail = "creator@gmail.com";
        String courtName = "잠실 농구장";

        User creator = User.builder()
                .email(creatorEmail)
                .nickname("creator")
                .socialId("creator-1234")
                .profileImage("s3에 저장된 이미지 url")
                .description("한줄 소개")
                .role(Role.USER)
                .proficiency(Proficiency.BEGINNER)
                .positions(Arrays.asList(Position.SG, Position.PG))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(creator);

        User receiver = User.builder()
                .email("receiver@gmail.com")
                .nickname("receiver")
                .socialId("receiver-1234")
                .profileImage("s3에 저장된 이미지 url")
                .description("한줄 소개")
                .role(Role.USER)
                .proficiency(Proficiency.BEGINNER)
                .positions(Arrays.asList(Position.SG, Position.PG))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        User receiverSaved = userRepository.save(receiver);

        Court courtEntity = new Court(
                courtName,
                1203.20302,
                2038.2939,
                "https://court-image",
                1,
                Texture.CONCRETE
        );
        courtRepository.save(courtEntity);

        Loudspeaker loudspeakerEntity = Loudspeaker.of(creator, courtEntity, LocalDateTime.now(), LocalDateTime.now());
        loudspeakerRepository.save(loudspeakerEntity);

        //When
        Notification notificationEntity = Notification.createLoudspeaker(receiverSaved.getId(), loudspeakerEntity);
        notificationRepository.save(notificationEntity);

        //Then
        assertThat(notificationEntity.getLoudSpeaker().getCourt().getName(), is(courtName));
        assertThat(notificationEntity.getLoudSpeaker().getUser().getEmail(), is(creatorEmail));
    }


}