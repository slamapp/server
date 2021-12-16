package org.slams.server.notification.repository;

import org.slams.server.notification.entity.NotificationIndex;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by yunyun on 2021/12/08.
 */

public interface NotificationIndexRepository extends JpaRepository<NotificationIndex, Long> {
    // 읽음 표시 하기
    // 알림 저장하기 -> loudspeaker, follow
    // userID에 맞게 아이디 메시지 추출하기 -> 무한 스크롤

    @Query("SELECT a.messageId FROM NotificationIndex a WHERE a.userId =:userId AND a.id >= :lastId ORDER BY a.createdAt ASC")
    List<String> findMessageIdByUserMoreThenAlarmIdByCreated(
            @Param("userId") Long userId,
            @Param("lastId") Long lastId,
            Pageable pageable
    );

    @Query("SELECT a.messageId FROM NotificationIndex a WHERE a.userId =:userId ORDER BY a.createdAt ASC")
    List<String> findMessageIdByUserByCreated(
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query("SELECT a.id FROM NotificationIndex a WHERE a.userId =:userId AND a.id >= :lastId ORDER BY a.createdAt ASC")
    List<Long> findIdByUserMoreThenAlarmIdByCreated(
            @Param("userId") Long userId,
            @Param("lastId") Long lastId,
            Pageable pageable
    );

    @Query("SELECT a.id FROM NotificationIndex a WHERE a.userId =:userId ORDER BY a.createdAt ASC")
    List<Long> findIdByUserByCreated(
            @Param("userId") Long userId,
            Pageable pageable
    );


//    @Query("select a from Notification a where a.userId=:userId order by a.createdAt")
//    List<Notification> findAllByUserId(
//            @Param("userId") Long userId
//    );
//
//    @Query("select a FROM Notification a where a.userId =:userId and a.id >= :lastId order by a.createdAt ASC")
//    List<Notification> findAllByUserMoreThenAlarmIdByCreated(
//            @Param("userId") Long userId,
//            @Param("lastId") Long lastId,
//            Pageable pageable
//    );
//
//    @Query("select a FROM Notification a where a.userId =:userId order by a.createdAt ASC")
//    List<Notification> findAllByUserByCreated(
//            @Param("userId") Long userId,
//            Pageable pageable
//    );
}