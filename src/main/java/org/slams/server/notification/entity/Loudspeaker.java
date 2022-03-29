package org.slams.server.notification.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slams.server.common.BaseEntity;
import org.slams.server.court.entity.Court;
import org.slams.server.notification.common.ValidationMessage;
import org.slams.server.user.entity.User;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by yunyun on 2021/12/14.
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "loudspeaker")
public class Loudspeaker extends BaseEntity {

    @Id
    @Column(name="id")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "court_id", referencedColumnName = "id")
    private Court court;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    public Loudspeaker(
            String id,
            User user,
            Court court,
            LocalDateTime startTime,
            LocalDateTime endTime
    ){
        checkArgument(id != null, ValidationMessage.NOTNULL_ID);
        checkArgument(user != null, ValidationMessage.NOTNULL_USER);
        checkArgument(court != null, ValidationMessage.NOTNULL_COURT);
        checkArgument(startTime != null, ValidationMessage.NOTNULL_START_TIME);
        checkArgument(endTime != null, ValidationMessage.NOTNULL_END_TIME);
        this.id = id;
        this.user = user;
        this.court = court;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private Loudspeaker(
            User user,
            Court court,
            LocalDateTime startTime,
            LocalDateTime endTime
    ){
        checkArgument(user != null, ValidationMessage.NOTNULL_USER);
        checkArgument(court != null, ValidationMessage.NOTNULL_COURT);
        checkArgument(startTime != null, ValidationMessage.NOTNULL_START_TIME);
        checkArgument(endTime != null, ValidationMessage.NOTNULL_END_TIME);
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.court = court;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Loudspeaker of(
            User user,
            Court court,
            LocalDateTime startTime,
            LocalDateTime endTime
    ){
        return new Loudspeaker(user, court, startTime, endTime);
    }
}
