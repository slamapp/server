package org.slams.server.reservation.entity;

import lombok.*;
import org.slams.server.common.BaseEntity;
import org.slams.server.court.entity.Court;
import org.slams.server.reservation.dto.request.ReservationUpdateRequestDto;
import org.slams.server.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by dongsung on 2021/12/03.
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="reservation")
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private boolean hasBall;

    @Builder
    public Reservation(Long id, Court court, User user, LocalDateTime startTime, LocalDateTime endTime, boolean hasBall) {
        validateTime(startTime, endTime);

        this.id = id;
        this.court = court;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hasBall = hasBall;
    }

    public void update(ReservationUpdateRequestDto request) {
        startTime=request.getStartTime();
        endTime=request.getEndTime();
        hasBall=request.getHasBall();
    }

    //== Validation Method ==//
    private void validateTime(LocalDateTime startTime, LocalDateTime endTime){
        if(startTime.isAfter(endTime)){
            throw new IllegalArgumentException("예약 시작시간이 예약 종료시간보다 미래일 수 없습니다.");
        }
    }

}
